package module;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Application {

	private static final Map<Class<?>, Object> INSTANCES = new ConcurrentHashMap<>();

	@SuppressWarnings("unchecked")
	public static <T> T getSingleton(Class<T> clazz) {
		if (INSTANCES.containsKey(clazz)) return (T) INSTANCES.get(clazz);

		synchronized (INSTANCES) {
			if (INSTANCES.containsKey(clazz)) return (T) INSTANCES.get(clazz);

			try {
				// Se for interface, tenta achar a implementação (ex: comum.FileService → service.FileServiceImpl)
				if (clazz.isInterface()) {
					String implName = "service." + clazz.getSimpleName() + "Impl";
					Class<?> implClass = Class.forName(implName);
					return (T) getSingleton((Class<T>) implClass);
				}

				// Resolve dependências via construtor
				Constructor<?> construtor = clazz.getDeclaredConstructors()[0];
				construtor.setAccessible(true);

				Object[] params = Arrays.stream(construtor.getParameterTypes())
						.map(Application::getSingleton)
						.toArray();

				Object instance = construtor.newInstance(params);
				INSTANCES.put(clazz, instance);
				return (T) instance;

			} catch (Exception e) {
				throw new RuntimeException("Erro ao criar singleton para: " + clazz.getName(), e);
			}
		}
	}

	public static <T> void registerSingleton(Class<T> clazz, T instance) {
		INSTANCES.put(clazz, instance);
	}

	public static void clear() {
		INSTANCES.clear();
	}
}
