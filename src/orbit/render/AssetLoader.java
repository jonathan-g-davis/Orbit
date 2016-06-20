package orbit.render;

import static org.lwjgl.stb.STBImage.stbi_failure_reason;
import static org.lwjgl.stb.STBImage.stbi_load;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.HashMap;
import java.util.Map;

import org.lwjgl.BufferUtils;

import orbit.texture.Texture;
import orbit.texture.TextureObject;

public class AssetLoader {
	
	private static Map<String, TextureObject> textures = new HashMap<String, TextureObject>();
	
	public static Texture createTexture(String file) {		
		IntBuffer w = BufferUtils.createIntBuffer(1);
		IntBuffer h = BufferUtils.createIntBuffer(1);
		IntBuffer comp = BufferUtils.createIntBuffer(1);
		
		//stbi_set_flip_vertically_on_load(1);
		
		ByteBuffer image = stbi_load("res/textures/" + file, w, h, comp, 4);
		if (image == null) {
			throw new RuntimeException("Failed to load \"" + file + "\": "
					+ System.lineSeparator() + stbi_failure_reason());
		}
		
		int width = w.get();
		int height = h.get();
		
		Texture texture = new Texture(width, height, image);
		textures.put(file, texture);
		return texture;
	}
	
	public static void dispose() {
		for (TextureObject tex : textures.values()) {
			tex.delete();
		}
	}
}
