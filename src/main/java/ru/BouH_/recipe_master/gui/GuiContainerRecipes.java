package ru.BouH_.recipe_master.gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiSlot;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.resources.I18n;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import ru.BouH_.gameplay.client.NotificationHud;
import ru.BouH_.init.BlocksZp;
import ru.BouH_.init.ItemsZp;
import ru.BouH_.recipe_master.RecipeMaster;
import ru.BouH_.recipe_master.data.RecipeInfo;
import ru.BouH_.recipe_master.data.RecipeType;
import ru.BouH_.recipe_master.data.container.ARecipeContainer;
import ru.BouH_.recipe_master.data.container.CommonRecipeContainer;
import ru.BouH_.recipe_master.data.container.FurnaceRecipeContainer;
import ru.BouH_.recipe_master.data.container.ShapelessRecipeContainer;
import ru.BouH_.skills.gui.GuiSkillsZp;
import ru.BouH_.utils.RenderUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Collectors;

@SideOnly(Side.CLIENT)
public class GuiContainerRecipes extends GuiScreen {
    private final boolean onlySpecial;
    protected GuiScreen guiScreen;
    private GuiContainerRecipes.List list;
    private int buttonsY;
    private GuiTextField guiTextField;

    public GuiContainerRecipes(GuiScreen p_i1043_1_, boolean onlySpecial) {
        this.guiScreen = p_i1043_1_;
        this.onlySpecial = onlySpecial;
    }

    public void updateScreen() {
        super.updateScreen();
        if (this.list.clickTicks > 0) {
            this.list.clickTicks -= 1;
        }
    }

    @SuppressWarnings("unchecked")
    public void initGui() {
        this.buttonList.add(new GuiButton(200, this.width / 2 - 100, this.height - 38, I18n.format("gui.done")));
        int x = this.width / 2 + 132;
        this.buttonsY = 96;
        this.buttonList.add(new GuiButton(1, x, this.buttonsY, 20, 20, "<<<"));
        this.buttonList.add(new GuiButton(2, x + 22, this.buttonsY, 20, 20, ">>>"));
        this.buttonList.add(new GuiButton(3, this.width / 2 - 150, this.height - 38, 40, 20, I18n.format("recipes.zp.return")));
        Keyboard.enableRepeatEvents(true);
        this.guiTextField = new GuiTextField(this.fontRendererObj, this.width / 2 + 110, this.height - 32, 90, this.fontRendererObj.FONT_HEIGHT);
        this.guiTextField.setMaxStringLength(14);
        this.guiTextField.setEnableBackgroundDrawing(true);
        this.guiTextField.setCanLoseFocus(false);
        this.guiTextField.setFocused(true);
        this.guiTextField.setText("");
        this.guiTextField.setVisible(true);
        this.guiTextField.setTextColor(0x00ff00);
        ArrayList<ItemStack> recipesList = new ArrayList<>();
        if (this.onlySpecial) {
            recipesList.addAll(RecipeMaster.instance.getSpecialMap().keySet());
            recipesList.sort((o1, o2) -> {
                int id1 = RecipeMaster.instance.getSpecialMap().get(o1).getSkillZp().getId();
                int id2 = RecipeMaster.instance.getSpecialMap().get(o2).getSkillZp().getId();
                int lvl1 = RecipeMaster.instance.getSpecialMap().get(o1).getLvl();
                int lvl2 = RecipeMaster.instance.getSpecialMap().get(o2).getLvl();
                int i1 = Item.getIdFromItem(o1.getItem());
                int i2 = Item.getIdFromItem(o2.getItem());
                if (id1 != id2) {
                    return id1 - id2;
                }
                return lvl1 == lvl2 ? i1 - i2 : lvl1 - lvl2;
            });
        } else {
            recipesList.addAll(RecipeMaster.instance.getListMap().keySet());
            recipesList.sort(Comparator.comparing(e -> Item.getIdFromItem(e.getItem())));
        }
        this.list = new GuiContainerRecipes.List(recipesList);
        this.list.registerScrollButtons(7, 8);
        ((GuiButton) this.buttonList.get(1)).visible = false;
        ((GuiButton) this.buttonList.get(2)).visible = false;
        ((GuiButton) this.buttonList.get(3)).visible = false;
    }

    public void onGuiClosed() {
        super.onGuiClosed();
        Keyboard.enableRepeatEvents(false);
    }

    protected void keyTyped(char typedChar, int keyCode) {
        if (this.guiTextField.textboxKeyTyped(typedChar, keyCode)) {
            this.list.sortList(this.guiTextField.getText());
        } else {
            super.keyTyped(typedChar, keyCode);
        }
    }

    public GuiTextField getGuiTextField() {
        return this.guiTextField;
    }

    protected void actionPerformed(GuiButton button) {
        if (button.id == 200) {
            this.mc.displayGuiScreen(this.onlySpecial ? new GuiSkillsZp(this.guiScreen) : this.guiScreen);
        }
        if (button.id == 1) {
            this.list.addPage(-1);
        }
        if (button.id == 2) {
            this.list.addPage(1);
        }
        if (button.id == 3) {
            this.list.currentSelected2 = null;
            this.list.recipesList = this.list.tempList;
            this.list.scrollBy(-this.list.getAmountScrolled());
            this.list.scrollBy(this.list.saveScroll);
        }
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.list.drawScreen(mouseX, mouseY, partialTicks);
        this.drawCenteredString(this.fontRendererObj, I18n.format("recipes.zp.menuTitle"), this.width / 2, 16, 0xffffff);
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.drawString(GuiContainerRecipes.this.fontRendererObj, I18n.format("recipes.zp.search"), this.width / 2 + 110, this.height - 42, 0x00ff00);
        this.drawString(GuiContainerRecipes.this.fontRendererObj, I18n.format("recipes.zp.tip1"), 4, 4, 0xf1d7ff);
        this.drawString(GuiContainerRecipes.this.fontRendererObj, I18n.format("recipes.zp.tip2"), 4, 14, 0xf1d7ff);
        this.guiTextField.drawTextBox();
        ((GuiButton) this.buttonList.get(1)).visible = this.list.maxP() > 1;
        ((GuiButton) this.buttonList.get(2)).visible = this.list.maxP() > 1;
        ((GuiButton) this.buttonList.get(3)).visible = this.list.currentSelected2 != null;
        ((GuiButton) this.buttonList.get(1)).yPosition = this.buttonsY;
        ((GuiButton) this.buttonList.get(2)).yPosition = this.buttonsY;
    }

    public class List extends GuiSlot {
        private final ArrayList<ItemStack> tempList;
        private ArrayList<ItemStack> recipesList = new ArrayList<>();
        private ItemStack currentSelected;
        private ItemStack currentSelected2;
        private int currentPage;
        private long pastSync;
        private int ranInt;
        private int clickTicks;
        private int saveScroll;

        public List(ArrayList<ItemStack> recipesList) {
            super(GuiContainerRecipes.this.mc, GuiContainerRecipes.this.width, GuiContainerRecipes.this.height, 32, GuiContainerRecipes.this.height - 48, 26);
            ArrayList<ItemStack> itemStackList = new ArrayList<>(recipesList);
            this.recipesList.addAll(itemStackList);
            this.tempList = new ArrayList<>(this.recipesList);
            this.currentSelected = null;
            this.currentSelected2 = null;
            this.currentPage = 0;
            this.saveScroll = 0;
        }

        public int maxP() {
            if (this.currentSelected != null) {
                return RecipeMaster.instance.getListMap().get(this.currentSelected).getPages();
            }
            return this.currentPage;
        }

        public void addPage(int i) {
            this.currentPage = MathHelper.clamp_int(this.currentPage + i, 0, this.maxP() - 1);
        }

        protected int getSize() {
            return this.recipesList.size();
        }

        protected void elementClicked(int p_148144_1_, boolean p_148144_2_, int p_148144_3_, int p_148144_4_) {
            ItemStack stack = this.recipesList.get(p_148144_1_);
            if (stack != null) {
                EntityPlayerSP entityPlayerSP = Minecraft.getMinecraft().thePlayer;
                this.currentPage = 0;
                if (this.currentSelected2 != this.currentSelected && RecipeMaster.instance.isRecipeOpened(entityPlayerSP, stack) && this.clickTicks > 0 && this.currentSelected != null && this.currentSelected == stack) {
                    if (this.currentSelected2 == null) {
                        this.saveScroll = this.getAmountScrolled();
                    }
                    this.currentSelected2 = this.currentSelected;
                    this.currentSelected = null;
                    this.scrollBy(-this.saveScroll);
                    this.sortList2(this.currentSelected2);
                } else {
                    this.currentSelected = this.recipesList.get(p_148144_1_);
                }
                this.clickTicks = 4;
            }
        }

        protected boolean isSelected(int p_148131_1_) {
            return false;
        }

        public int getListWidth() {
            return 236;
        }

        protected int getContentHeight() {
            return this.getSize() * 26;
        }

        protected void drawSelectionBox(int p_148120_1_, int p_148120_2_, int p_148120_3_, int p_148120_4_) {
            super.drawSelectionBox(p_148120_1_, p_148120_2_, p_148120_3_, p_148120_4_);
            if (this.currentSelected != null) {
                this.drawCraftingMatrix(this.currentSelected);
            }
        }

        protected void drawBackground() {
            GuiContainerRecipes.this.drawDefaultBackground();
            if ((System.currentTimeMillis() - pastSync) >= 1000) {
                if (ranInt++ >= 128) {
                    ranInt = 0;
                }
                pastSync = System.currentTimeMillis();
            }
        }

        public void sortList(String text) {
            String finalText = text.toLowerCase();
            EntityPlayerSP entityPlayerSP = Minecraft.getMinecraft().thePlayer;
            if (!text.isEmpty()) {
                this.recipesList = this.tempList.stream().filter(e -> RecipeMaster.instance.isRecipeOpened(entityPlayerSP, e) && e.getDisplayName().toLowerCase().contains(finalText)).collect(Collectors.toCollection(ArrayList::new));
            } else {
                this.recipesList = this.tempList;
            }
            if (this.currentSelected != null && !this.recipesList.contains(this.currentSelected)) {
                this.recipesList.add(this.currentSelected);
            }
        }

        public void sortList2(ItemStack selectedItem) {
            EntityPlayerSP entityPlayerSP = Minecraft.getMinecraft().thePlayer;
            ArrayList<ItemStack> arrayList = new ArrayList<>();
            ArrayList<ItemStack> arrayList1 = this.tempList.stream().filter(e -> {
                if (!RecipeMaster.instance.isRecipeOpened(entityPlayerSP, e)) {
                    return false;
                }
                RecipeInfo recipeInfo = RecipeMaster.instance.getListMap().get(e);
                ArrayList<ARecipeContainer> aRecipeContainerList = (ArrayList<ARecipeContainer>) recipeInfo.getiRecipeContainerList();
                for (ARecipeContainer aRecipeContainer : aRecipeContainerList) {
                    if (e != this.currentSelected2 && RecipeMaster.instance.checkItemInCollection(selectedItem, aRecipeContainer.getArrayList()) != null) {
                        return true;
                    }
                }
                return false;
            }).collect(Collectors.toCollection(ArrayList::new));
            arrayList.add(this.currentSelected2);
            arrayList.addAll(arrayList1);
            this.recipesList = arrayList;
        }

        protected void drawSlot(int p_148126_1_, int p_148126_2_, int p_148126_3_, int p_148126_4_, Tessellator p_148126_5_, int p_148126_6_, int p_148126_7_) {
            ItemStack stack = this.recipesList.get(p_148126_1_);
            boolean flag = this.currentSelected == stack;
            EntityPlayerSP entityPlayerSP = Minecraft.getMinecraft().thePlayer;
            if (stack != null) {
                boolean isClosed = !RecipeMaster.instance.isRecipeOpened(entityPlayerSP, stack);
                GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                mc.getTextureManager().bindTexture(NotificationHud.component);
                GuiContainerRecipes.this.drawTexturedModalRect(this.width / 2 - 118, p_148126_3_, 26, flag ? 228 : 202, 52, 26);
                if (isClosed) {
                    RecipeMaster.SpecialRecipePair specialRecipePair = RecipeMaster.instance.getSpecialMap().get(stack);
                    GuiContainerRecipes.this.drawTexturedModalRect(this.width / 2 - 146, p_148126_3_, 26, 202, 52, 26);
                    //String s1 = specialRecipePair.getSkillZp().getName();
                    String s2 = "Lvl " + specialRecipePair.getLvl();
                    //GuiContainerRecipes.this.drawString(GuiContainerRecipes.this.fontRendererObj, s1, this.width / 2 - 148 - GuiContainerRecipes.this.fontRendererObj.getStringWidth(s1), p_148126_3_ + 4, 0xff0000);
                    GuiContainerRecipes.this.drawString(GuiContainerRecipes.this.fontRendererObj, s2, this.width / 2 - 146 - GuiContainerRecipes.this.fontRendererObj.getStringWidth(s2), p_148126_3_ + 9, 0xff0000);
                    RenderUtils.renderIcon((double) this.width / 2 - 141, p_148126_3_ + 5, new ItemStack(specialRecipePair.getSkillZp().getLogo()));
                }
                if (isClosed) {
                    if (stack.getItem() instanceof ItemBlock) {
                        RenderUtils.renderIconBlockBlack((double) this.width / 2 - 113, p_148126_3_ + 5, stack);
                    } else {
                        RenderUtils.renderIconBlack((double) this.width / 2 - 113, p_148126_3_ + 5, stack);
                    }
                } else {
                    RenderUtils.renderIcon((double) this.width / 2 - 113, p_148126_3_ + 5, stack);
                }
                GuiContainerRecipes.this.drawString(GuiContainerRecipes.this.fontRendererObj, isClosed ? "???" : stack.getDisplayName(), this.width / 2 - 92, p_148126_3_ + 9, flag ? 0x00ff00 : this.currentSelected2 == stack ? 0xff00ff : (isClosed ? 0xff0000 : RecipeMaster.instance.getSpecialMap().containsKey(stack) ? 0xa1dfff : 0xffffff));
                GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            }
        }

        private void drawCraftingMatrix(ItemStack itemStack) {
            RecipeInfo recipeInfo = RecipeMaster.instance.getListMap().get(itemStack);
            ARecipeContainer aRecipeContainer = recipeInfo.getiRecipeContainerList().get(this.currentPage);
            EntityPlayerSP entityPlayerSP = Minecraft.getMinecraft().thePlayer;
            boolean isClosed = !RecipeMaster.instance.isRecipeOpened(entityPlayerSP, itemStack);
            int x = this.width / 2 + 120;
            int y = 76;
            mc.getTextureManager().bindTexture(NotificationHud.component);
            GuiContainerRecipes.this.drawTexturedModalRect(x + 11, 36, 26, 228, 52, 26);
            if (isClosed) {
                if (itemStack.getItem() instanceof ItemBlock) {
                    RenderUtils.renderIconBlockBlack(x + 16, y - 35, itemStack);
                } else {
                    RenderUtils.renderIconBlack(x + 16, y - 35, itemStack);
                }
                GuiContainerRecipes.this.drawString(GuiContainerRecipes.this.fontRendererObj, I18n.format("recipes.zp.lvl_required"), x + 16, y - 12, 0xff0000);
            } else {
                RenderUtils.renderIcon(x + 16, y - 35, itemStack);
                GuiContainerRecipes.this.drawString(GuiContainerRecipes.this.fontRendererObj, itemStack.getDisplayName(), x + 16, y - 12, RecipeMaster.instance.getSpecialMap().containsKey(itemStack) ? 0xa1dfff : 0x00ff00);
                GuiContainerRecipes.this.drawString(GuiContainerRecipes.this.fontRendererObj, aRecipeContainer.getStackSize() + "x", x + 36, y - 24, 0x00ff00);
                GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                ArrayList<ItemStack> itemStackList = new ArrayList<>();
                int minY = y + 28;
                int k = 0;
                if (aRecipeContainer.getType() == RecipeType.SHAPED) {
                    CommonRecipeContainer commonRecipeContainer = (CommonRecipeContainer) aRecipeContainer;
                    minY = y + commonRecipeContainer.getHeight() * 28;
                    for (int i = 0; i < commonRecipeContainer.getHeight(); i++) {
                        for (int j = 0; j < commonRecipeContainer.getWidth(); j++) {
                            ItemStack stack = aRecipeContainer.getArrayList().get(k++);
                            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                            mc.getTextureManager().bindTexture(NotificationHud.component);
                            GuiContainerRecipes.this.drawTexturedModalRect((int) ((double) (x + 11) + j * 26), (int) ((double) (y + 11) + i * 26), 26, 202, 52, 26);
                            if (stack != null) {
                                if (stack.getHasSubtypes() && stack.getMetadata() == 32767) {
                                    ArrayList<ItemStack> stackList = new ArrayList<>();
                                    stack.getItem().getSubItems(stack.getItem(), null, stackList);
                                    stack = new ItemStack(stack.getItem(), stack.stackSize, Math.floorMod(this.ranInt, stackList.size()));
                                }
                                if (this.checkItemInSet(itemStackList, stack)) {
                                    itemStackList.add(stack);
                                }
                                RenderUtils.renderIcon((double) x + j * 26 + 16, (double) y + i * 26 + 16, stack);
                            }
                        }
                    }
                } else if (aRecipeContainer.getType() == RecipeType.SHAPELESS) {
                    ShapelessRecipeContainer shapelessRecipeContainer = (ShapelessRecipeContainer) aRecipeContainer;
                    int i1 = shapelessRecipeContainer.getArrayList().size();
                    minY = y + (i1 > 6 ? 3 : i1 > 3 ? 2 : 1) * 28;
                    for (ItemStack stack : shapelessRecipeContainer.getArrayList()) {
                        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                        mc.getTextureManager().bindTexture(NotificationHud.component);
                        GuiContainerRecipes.this.drawTexturedModalRect(x + 11, y + 11, 26, 202, 52, 26);
                        if (stack != null) {
                            if (stack.getHasSubtypes() && stack.getMetadata() == 32767) {
                                ArrayList<ItemStack> stackList = new ArrayList<>();
                                stack.getItem().getSubItems(stack.getItem(), null, stackList);
                                stack = new ItemStack(stack.getItem(), stack.stackSize, Math.floorMod(this.ranInt, stackList.size()));
                            }
                            if (this.checkItemInSet(itemStackList, stack)) {
                                itemStackList.add(stack);
                            }
                            RenderUtils.renderIcon(x + 16, y + 16, stack);
                        }
                        if (++k % 3 == 0) {
                            x = this.width / 2 + 120;
                            y += 28;
                        } else {
                            x += 28;
                        }
                    }
                } else if (aRecipeContainer.getType() == RecipeType.FURNACE) {
                    FurnaceRecipeContainer furnaceRecipeContainer = (FurnaceRecipeContainer) aRecipeContainer;
                    ItemStack stack = furnaceRecipeContainer.getArrayList().get(0);
                    if (stack != null) {
                        if (stack.getHasSubtypes() && stack.getMetadata() == 32767) {
                            ArrayList<ItemStack> stackList = new ArrayList<>();
                            stack.getItem().getSubItems(stack.getItem(), null, stackList);
                            stack = new ItemStack(stack.getItem(), stack.stackSize, Math.floorMod(this.ranInt, stackList.size()));
                        }
                        minY = 104;
                        GuiContainerRecipes.this.drawString(GuiContainerRecipes.this.fontRendererObj, ">>>", x + 38, y + 20, 0x00ff00);
                        GuiContainerRecipes.this.drawString(GuiContainerRecipes.this.fontRendererObj, ">>>", x + 81, y + 20, 0x00ff00);
                        Item item = stack.getItem();
                        String s2 = null;
                        if (item == ItemsZp.scrap_material) {
                            s2 = "30%";
                        } else if (item == Item.getItemFromBlock(BlocksZp.uranium)) {
                            s2 = "5%";
                        } else if (item == Items.rotten_flesh) {
                            s2 = "40%";
                        }
                        if (s2 != null) {
                            GuiContainerRecipes.this.drawString(GuiContainerRecipes.this.fontRendererObj, s2, x + 128, y + 20, 0xffffff);
                        }
                        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                        mc.getTextureManager().bindTexture(NotificationHud.component);
                        GuiContainerRecipes.this.drawTexturedModalRect(x + 11, y + 11, 26, 202, 52, 26);
                        GuiContainerRecipes.this.drawTexturedModalRect(x + 55, y + 11, 26, 202, 52, 26);
                        GuiContainerRecipes.this.drawTexturedModalRect(x + 99, y + 11, 26, 228, 52, 26);
                        if (this.checkItemInSet(itemStackList, stack)) {
                            itemStackList.add(stack);
                        }
                        RenderUtils.renderIcon(x + 104, y + 16, itemStack);
                        RenderUtils.renderIcon(x + 60, y + 16, new ItemStack(Blocks.furnace));
                        RenderUtils.renderIcon(x + 16, y + 16, stack);
                    }
                }
                String s1 = I18n.format("recipes.zp.page") + ": " + (this.currentPage + 1) + "/" + recipeInfo.getPages();
                GuiContainerRecipes.this.drawString(GuiContainerRecipes.this.fontRendererObj, s1, this.width / 2 + 134, minY + 12, 0xffffff);
                GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                GuiContainerRecipes.this.buttonsY = minY + 24;
                x = 2;
                y = 36;
                itemStackList.sort(Comparator.comparing(e -> Item.getIdFromItem(e.getItem())));
                for (ItemStack stack : itemStackList) {
                    GuiContainerRecipes.this.drawString(GuiContainerRecipes.this.fontRendererObj, stack.getDisplayName(), x + 37, y + 19, 0xffffff);
                    mc.getTextureManager().bindTexture(NotificationHud.component);
                    GuiContainerRecipes.this.drawTexturedModalRect(x + 11, y + 11, 26, 202, 52, 26);
                    RenderUtils.renderIcon(x + 16, y + 16, stack);
                    y += 28;
                }
            }
        }

        private boolean checkItemInSet(ArrayList<ItemStack> stacks, ItemStack itemStack) {
            return stacks.stream().noneMatch(e -> (e.getItem() == itemStack.getItem() && e.getMetadata() == itemStack.getMetadata()));
        }
    }
}