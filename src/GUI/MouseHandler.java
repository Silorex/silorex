package GUI;

import RenderEngine.Camera;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.system.MemoryStack;

import java.nio.IntBuffer;

import static org.lwjgl.glfw.GLFW.glfwGetWindowSize;
import static org.lwjgl.system.MemoryStack.stackPush;

public class MouseHandler extends GLFWCursorPosCallback
{
	@Override
	public void invoke(long window, double x, double y)
	{
		double movedX = 0, movedY = 0;
		IntBuffer pWidth, pHeight;
		try(MemoryStack stack = stackPush())
		{
			pWidth = stack.mallocInt(1);
			pHeight = stack.mallocInt(1);
			glfwGetWindowSize(window, pWidth, pHeight);
			movedX = pWidth.get(0)/2 - x;
			movedY = pHeight.get(0)/2 - y;
		}
		if(MenuHandler.isAnyMenuShown() == false)
		{
			GLFW.glfwSetCursorPos(window, pWidth.get(0)/2, pHeight.get(0)/2);
			Camera.MoveCamera(movedX, movedY);
		}
		//System.out.println("Moved: " + movedX + ", " + movedY);

	}
}
