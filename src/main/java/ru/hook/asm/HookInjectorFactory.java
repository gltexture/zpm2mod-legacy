package ru.hook.asm;

import org.objectweb.asm.MethodVisitor;

/**
 * Р¤Р°Р±СЂРёРєР°, Р·Р°РґР°СЋС‰Р°СЏ С‚РёРї РёРЅР¶РµРєС‚РѕСЂР° С…СѓРєРѕРІ. Р¤Р°РєС‚РёС‡РµСЃРєРё, РѕС‚ РІС‹Р±РѕСЂР° С„Р°Р±СЂРёРєРё Р·Р°РІРёСЃРёС‚ С‚Рѕ, РІ РєР°РєРёРµ СѓС‡Р°СЃС‚РєРё РєРѕРґР° РїРѕРїР°РґС‘С‚ С…СѓРє.
 * "Р�Р· РєРѕСЂРѕР±РєРё" РґРѕСЃС‚СѓРїРЅРѕ РґРІР° С‚РёРїР° РёРЅР¶РµРєС‚РѕСЂРѕРІ: MethodEnter, РєРѕС‚РѕСЂС‹Р№ РІСЃС‚Р°РІР»СЏРµС‚ С…СѓРє РЅР° РІС…РѕРґРµ РІ РјРµС‚РѕРґ,
 * Рё MethodExit, РєРѕС‚РѕСЂС‹Р№ РІСЃС‚Р°РІР»СЏРµС‚ С…СѓРє РЅР° РєР°Р¶РґРѕРј РІС‹С…РѕРґРµ.
 */
public abstract class HookInjectorFactory {

    /**
     * РњРµС‚РѕРґ AdviceAdapter#visitInsn() - С€С‚СѓРєР° СЃС‚СЂР°РЅРЅР°СЏ. РўР°Рј РїРѕС‡РµРјСѓ-С‚Рѕ РІС‹Р·РѕРІ СЃР»РµРґСѓСЋС‰РµРіРѕ MethodVisitor'a
     * РїСЂРѕРёР·РІРѕРґРёС‚СЃСЏ РїРѕСЃР»Рµ Р»РѕРіРёРєРё, Р° РЅРµ РґРѕ, РєР°Рє РІРѕ РІСЃРµС… РѕСЃС‚Р°Р»СЊРЅС‹С… СЃР»СѓС‡Р°СЏС…. РџРѕСЌС‚РѕРјСѓ РґР»СЏ MethodExit РїСЂРёРѕСЂРёС‚РµС‚
     * С…СѓРєРѕРІ РёРЅРІРµСЂС‚РёСЂСѓРµС‚СЃСЏ.
     */
    protected boolean isPriorityInverted = false;

    abstract HookInjectorMethodVisitor createHookInjector(MethodVisitor mv, int access, String name, String desc,
                                                          AsmHook hook, HookInjectorClassVisitor cv);


    static class MethodEnter extends HookInjectorFactory {

        public static final MethodEnter INSTANCE = new MethodEnter();

        private MethodEnter() {
        }

        @Override
        public HookInjectorMethodVisitor createHookInjector(MethodVisitor mv, int access, String name, String desc,
                                                            AsmHook hook, HookInjectorClassVisitor cv) {
            return new HookInjectorMethodVisitor.MethodEnter(mv, access, name, desc, hook, cv);
        }

    }

    static class MethodExit extends HookInjectorFactory {

        public static final MethodExit INSTANCE = new MethodExit();

        private MethodExit() {
            isPriorityInverted = true;
        }

        @Override
        public HookInjectorMethodVisitor createHookInjector(MethodVisitor mv, int access, String name, String desc,
                                                            AsmHook hook, HookInjectorClassVisitor cv) {
            return new HookInjectorMethodVisitor.MethodExit(mv, access, name, desc, hook, cv);
        }
    }

    static class LineNumber extends HookInjectorFactory {

        private final int lineNumber;

        public LineNumber(int lineNumber) {
            this.lineNumber = lineNumber;
        }

        @Override
        public HookInjectorMethodVisitor createHookInjector(MethodVisitor mv, int access, String name, String desc,
                                                            AsmHook hook, HookInjectorClassVisitor cv) {
            return new HookInjectorMethodVisitor.LineNumber(mv, access, name, desc, hook, cv, lineNumber);
        }
    }

}
