package ru.clientfixer.asm;

import net.minecraft.launchwrapper.IClassTransformer;
import ru.hook.asm.HookClassTransformer;

public class ASMTransformer extends HookClassTransformer implements IClassTransformer {

	@Override
	public byte[] transform(String name, String transformedName, byte[] basicClass) {
		if (transformedName.equals("net.minecraft.client.resources.Locale")) {
			return FatRussianFont.patchLocale(basicClass);
		}
		if (transformedName.equals("net.minecraft.client.gui.FontRenderer")) {
			return FatRussianFont.patchFontRenderer(basicClass);
		}
		if (transformedName.equals("net.minecraft.client.Minecraft")) {
			return FatRussianFont.patchMinecraft(basicClass);
		}
		return basicClass;
	}
}