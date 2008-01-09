package org.microemu.android.asm;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;

public class AndroidProducer {

	private static byte[] instrument(final InputStream classInputStream) throws IOException {
		ClassReader cr = new ClassReader(classInputStream);
		ClassWriter cw = new ClassWriter(0);
		ClassVisitor cv = new AndroidClassVisitor(cw);
		cr.accept(cv, 0);

		return cw.toByteArray();
    }

	public static void processJar(File jarInputFile, File jarOutputFile) throws IOException {
		JarInputStream jis = null;
		JarOutputStream jos = null;
		try {
			jis = new JarInputStream(new FileInputStream(jarInputFile));
			Manifest manifest = jis.getManifest();
			if (manifest == null) {
				jos = new JarOutputStream(new FileOutputStream(jarOutputFile));
			} else {
				jos = new JarOutputStream(new FileOutputStream(jarOutputFile), manifest);
			}
		
			byte[] inputBuffer = new byte[1024];
			JarEntry jarEntry;
			while ((jarEntry = jis.getNextJarEntry()) != null) {
				if (jarEntry.isDirectory() == false) {
					String name = jarEntry.getName();
					int size = 0;
					int read;
					int length = inputBuffer.length;
					while ((read = jis.read(inputBuffer, size, length)) > 0) {
						size += read;
						
						length = 1024;
						if (size + length > inputBuffer.length) {
							byte[] newInputBuffer = new byte[size + length];
							System.arraycopy(inputBuffer, 0, newInputBuffer, 0, inputBuffer.length);
							inputBuffer = newInputBuffer;
						}
					}
					
					byte[] outputBuffer = inputBuffer;
					int outputSize = size;
					if (name.endsWith(".class")) {					
				        outputBuffer = instrument(new ByteArrayInputStream(inputBuffer, 0, size));
				        outputSize = outputBuffer.length;
				        name = AndroidClassVisitor.fixPackage(name);
					}
					jos.putNextEntry(new JarEntry(name));
					jos.write(outputBuffer, 0, outputSize);
				}
			}			
		} finally {
			jis.close();
			jos.close();
		}
	}
	
	public static void main(String args[]) {
		if (args.length != 2) {
			System.out.println("usage: AndroidProducer <infile> <outfile>");
		} else {
			try {
				processJar(new File(args[0]), new File(args[1]));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
