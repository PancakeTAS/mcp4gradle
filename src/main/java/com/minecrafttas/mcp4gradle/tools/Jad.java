package com.minecrafttas.mcp4gradle.tools;

import java.io.File;
import java.lang.ProcessBuilder.Redirect;

import com.minecrafttas.mcp4gradle.Utils;

/**
 * Jad wrapper class
 */
public class Jad {

	private static final String JAD_EXE = "https://data.mgnet.work/mcp4gradle/tools/jad.exe";
	
	private File dir;
	private ProcessBuilder p1, p2;
	
	/**
	 * Initializes Jad
	 * @param dir Directory
	 */
	public Jad(File dir) {
		this.dir = dir;
	}
	
	/**
	 * Initialize the decompiler
	 * @throws Exception Filesystem Exception
	 */
	public void init() throws Exception {
		var jad = Utils.obtainTempFile(JAD_EXE);
		
		this.p1 = new ProcessBuilder(jad.getAbsolutePath(), "-b", "-d", "src/minecraft", "-dead", "-o", "-r", "-s", ".java", "-stat", "-v", "-ff", "bin/minecraft/net/minecraft/client/*.class");
		this.p1.directory(this.dir);
		this.p1.redirectOutput(Redirect.DISCARD);
		this.p1.redirectError(Redirect.DISCARD);

		this.p2 = new ProcessBuilder(jad.getAbsolutePath(), "-b", "-d", "src/minecraft", "-dead", "-o", "-r", "-s", ".java", "-stat", "-v", "-ff", "bin/minecraft/net/minecraft/src/*.class");
		this.p2.directory(this.dir);
		this.p2.redirectOutput(Redirect.DISCARD);
		this.p2.redirectError(Redirect.DISCARD);
		
		new File(this.dir, "src/minecraft").mkdirs();
	}
	
	/**
	 * Runs the decompiler
	 * @throws Exception
	 */
	public void run() throws Exception {
		this.p1.start().waitFor();
		this.p2.start().waitFor();
	}
	
}