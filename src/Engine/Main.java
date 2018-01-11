package Engine;

import Entities.Entity;
import Models.TexturedModel;
import RenderEngine.Camera;
import RenderEngine.Loader;
import Models.RawModel;
import RenderEngine.Renderer;
import Shaders.StaticShader;
import Models.ModelTexture;
import Toolbox.Vector3;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;
import java.nio.*;
import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;

public class Main
{

	public static boolean[] keys = new boolean[65536];


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

			keys[key] = action != GLFW.GLFW_RELEASE;

			if(key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE)
			{
				glfwSetWindowShouldClose(window, true);
			}
		});

		//GLFWKeyCallback keyCallback;
		//glfwSetKeyCallback(window, keyCallback = new KeyboardHandler());

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
		shader = new StaticShader();
		IntBuffer pWidth, pHeight;
		try(MemoryStack stack = stackPush())
		{
			pWidth = stack.mallocInt(1);
			pHeight = stack.mallocInt(1);
			glfwGetWindowSize(window, pWidth, pHeight);
		}
		renderer = new Renderer(shader, (float) pWidth.get() / (float) pHeight.get());
	}

	private void loop() {
		GL.createCapabilities();
		GLUtil.setupDebugMessageCallback();

		myInit();

		float[] vertices = {
				-0.5f,0.5f,-0.5f,
				-0.5f,-0.5f,-0.5f,
				0.5f,-0.5f,-0.5f,
				0.5f,0.5f,-0.5f,

				-0.5f,0.5f,0.5f,
				-0.5f,-0.5f,0.5f,
				0.5f,-0.5f,0.5f,
				0.5f,0.5f,0.5f,

				0.5f,0.5f,-0.5f,
				0.5f,-0.5f,-0.5f,
				0.5f,-0.5f,0.5f,
				0.5f,0.5f,0.5f,

				-0.5f,0.5f,-0.5f,
				-0.5f,-0.5f,-0.5f,
				-0.5f,-0.5f,0.5f,
				-0.5f,0.5f,0.5f,

				-0.5f,0.5f,0.5f,
				-0.5f,0.5f,-0.5f,
				0.5f,0.5f,-0.5f,
				0.5f,0.5f,0.5f,

				-0.5f,-0.5f,0.5f,
				-0.5f,-0.5f,-0.5f,
				0.5f,-0.5f,-0.5f,
				0.5f,-0.5f,0.5f

		};

		float[] textures = {

				0,0,
				0,1,
				1,1,
				1,0,
				0,0,
				0,1,
				1,1,
				1,0,
				0,0,
				0,1,
				1,1,
				1,0,
				0,0,
				0,1,
				1,1,
				1,0,
				0,0,
				0,1,
				1,1,
				1,0,
				0,0,
				0,1,
				1,1,
				1,0


		};

		int[] indices = {
				0,1,3,
				3,1,2,
				4,5,7,
				7,5,6,
				8,9,11,
				11,9,10,
				12,13,15,
				15,13,14,
				16,17,19,
				19,17,18,
				20,21,23,
				23,21,22

		};
		RawModel model = loader.loadToVAO(vertices, indices, textures);
		ModelTexture texture = new ModelTexture(loader.loadTexture("abcd"));
		TexturedModel texturedModel = new TexturedModel(model, texture);
		Entity entity = new Entity(texturedModel, new Vector3(0, 0, -5), new Vector3(0, 0.5f, 0), 1f);

		Camera camera = new Camera();

		while(!glfwWindowShouldClose(window))
		{
			//entity.increasePosition(0, 0, -0.02f);
			entity.increaseRotation(1, 1, 0);

			camera.move();

			renderer.prepare();

			shader.start();
			shader.loadViewMatrix(camera);
			renderer.render(entity, shader);
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