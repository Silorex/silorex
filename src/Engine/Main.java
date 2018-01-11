package Engine;

import Models.TexturedModel;
import RenderEngine.Loader;
import Models.RawModel;
import RenderEngine.Renderer;
import Shaders.StaticShader;
import Textures.ModelTexture;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;
import java.nio.*;
import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;

public class Main {

	private long window;
	private Loader loader;
	private Renderer renderer;
	private StaticShader shader;

	public void run()
	{
		init();
		loop();

		glfwFreeCallbacks(window);
		glfwDestroyWindow(window);
		glfwTerminate();
		glfwSetErrorCallback(null).free();

		Loader.cleanUp();
		shader.cleanUp();
	}

	private void init()
	{
		GLFWErrorCallback.createPrint(System.err).set();

		if (!glfwInit()) throw new IllegalStateException("Unable to initialize GLFW");

		glfwDefaultWindowHints();
		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
		glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
		glfwWindowHint(GLFW_OPENGL_DEBUG_CONTEXT, GLFW_TRUE);

		window = glfwCreateWindow(1280, 720, "Silorex", NULL, NULL);
		if (window == NULL)
		{
			throw new RuntimeException("Failed to create the GLFW window");
		}

		glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
			if(key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE)
			{
				glfwSetWindowShouldClose(window, true);
			}
		});

		try(MemoryStack stack = stackPush())
		{
			IntBuffer pWidth = stack.mallocInt(1);
			IntBuffer pHeight = stack.mallocInt(1);
			glfwGetWindowSize(window, pWidth, pHeight);
			GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
			glfwSetWindowPos(window, (vidmode.width() - pWidth.get(0)) / 2,(vidmode.height() - pHeight.get(0)) / 2);
		}

		glfwMakeContextCurrent(window);
		glfwSwapInterval(1);
		glfwShowWindow(window);
	}

	private void myInit()
	{
		loader = new Loader();
		renderer = new Renderer();
		shader = new StaticShader();
	}

	private void loop() {
		GL.createCapabilities();
		GLUtil.setupDebugMessageCallback();

		myInit();

		float[] vertices = { -0.5f, 0.5f, 0f, -0.5f, -0.5f, 0f, 0.5f, -0.5f, 0f, 0.5f, 0.5f, 0f };
		int[] indices = {0, 1, 3, 3, 1, 2};
		float[] textures = {0, 0, 0, 1, 1, 1, 1, 0};
		RawModel model = loader.loadToVAO(vertices, indices, textures);
		ModelTexture texture = new ModelTexture(loader.loadTexture("abcd"));
		TexturedModel texturedModel = new TexturedModel(model, texture);

		while(!glfwWindowShouldClose(window))
		{
			renderer.prepare();

			shader.start();
			renderer.render(texturedModel);
			shader.stop();

			glfwSwapBuffers(window);
			glfwPollEvents();
		}
	}

	public static void main(String[] args)
	{
		new Main().run();
	}

}