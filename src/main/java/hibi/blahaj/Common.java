package hibi.blahaj;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;

import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;
import java.util.function.Consumer;

import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.SoundType;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mod("blahaj")
public class Common {
	private static final Logger LOGGER = LoggerFactory.getLogger("Blahaj");

	public static final String MODID = "blahaj";

	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);
	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);
	public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);

	public static final RegistryObject<Block> GRAY_SHARK_BLOCK = BLOCKS.register("gray_shark",
		() -> new CuddlyBlock(BlockBehaviour.Properties.of()
			.strength(0.8F)
			.noOcclusion()
			.ignitedByLava()
			.sound(SoundType.WOOL)));

	public static final RegistryObject<Block> BLUE_SHARK_BLOCK = BLOCKS.register("blue_shark",
		() -> new CuddlyBlock(BlockBehaviour.Properties.of()
			.strength(0.8F)
			.noOcclusion()
			.ignitedByLava()
			.sound(SoundType.WOOL)));

	// 注册物品
	public static final RegistryObject<Item> BLUE_WHALE = ITEMS.register("blue_whale",
		() -> new ItemContainerCuddlyItem(new Item.Properties().stacksTo(1), "item.blahaj.blue_whale.tooltip"));

	public static final RegistryObject<Item> BREAD_PILLOW = ITEMS.register("bread",
		() -> new SimpleCuddlyItem(new Item.Properties().stacksTo(1), null));

	public static final RegistryObject<Item> BROWN_BEAR = ITEMS.register("brown_bear",
		() -> new SimpleCuddlyItem(new Item.Properties().stacksTo(1), "item.blahaj.brown_bear.tooltip"));

	// 注册方块物品
	public static final RegistryObject<Item> GRAY_SHARK = ITEMS.register("gray_shark",
		() -> new CuddlyBlockItem(GRAY_SHARK_BLOCK.get(), new Item.Properties().stacksTo(1)));

	public static final RegistryObject<Item> BLUE_SHARK = ITEMS.register("blue_shark",
		() -> new CuddlyBlockItem(BLUE_SHARK_BLOCK.get(), new Item.Properties().stacksTo(1)));

	public Common() {
		IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
		BLOCKS.register(modEventBus);
		ITEMS.register(modEventBus);
		CREATIVE_MODE_TABS.register(modEventBus);

		modEventBus.addListener(this::setup);
	}

	private void setup(final FMLCommonSetupEvent event) {
		LOGGER.info("正在前往宜家购买鲨鱼...");
	}

	// 创造模式物品栏
	public static final RegistryObject<CreativeModeTab> BLAHAJ_TAB = CREATIVE_MODE_TABS.register("blahaj_tab",
		() -> CreativeModeTab.builder()
			.title(Component.translatable("itemGroup.blahaj"))
			.icon(() -> new ItemStack(BLUE_SHARK.get()))
			.displayItems((parameters, output) -> {
				output.accept(GRAY_SHARK.get());
				output.accept(BLUE_SHARK.get());
				output.accept(BLUE_WHALE.get());
				output.accept(BREAD_PILLOW.get());
				output.accept(BROWN_BEAR.get());
			}).build());

	public static final ResourceLocation BLAHAJ_ID = new ResourceLocation("blahaj", "blue_shark");
	public static final ResourceLocation KLAPPAR_HAJ_ID = new ResourceLocation("blahaj", "gray_shark");
	public static final ResourceLocation BLAVINGAD_ID = new ResourceLocation("blahaj", "blue_whale");
	public static final ResourceLocation BREAD_ID = new ResourceLocation("blahaj", "bread");
	public static final ResourceLocation BROWN_BEAR_ID = new ResourceLocation("blahaj", "brown_bear");

	@SubscribeEvent
	public void onLootTableLoad(LootTableLoadEvent event) {
		ResourceLocation tableLocation = event.getName();
		if (tableLocation.equals(new ResourceLocation("minecraft:chests/stronghold_crossing"))) {
			event.getTable().addPool(LootPool.lootPool()
				.add(LootItem.lootTableItem(GRAY_SHARK.get()).setWeight(5))
				.add(LootItem.lootTableItem(Items.AIR).setWeight(100))
				.build());
		}
		else if(tableLocation.equals(new ResourceLocation("minecraft:chests/village/plains"))) {
			event.getTable().addPool(LootPool.lootPool()
				.add(LootItem.lootTableItem(GRAY_SHARK.get()))
				.add(LootItem.lootTableItem(Items.AIR).setWeight(43))
				.build());
		}
		else if(tableLocation.equals(new ResourceLocation("minecraft:chests/village/taiga"))) {
			event.getTable().addPool(LootPool.lootPool()
				.add(LootItem.lootTableItem(GRAY_SHARK.get()).setWeight(5))
				.add(LootItem.lootTableItem(Items.AIR).setWeight(54))
				.build());
		}
	}

	@SubscribeEvent
	public void onVillagerTrades(VillagerTradesEvent event) {
		if (event.getType() == VillagerProfession.SHEPHERD) {
			List<VillagerTrades.ItemListing> trades = event.getTrades().get(5);
			trades.add((trader, rand) -> new MerchantOffer(
				new ItemStack(Items.EMERALD, 15),
				new ItemStack(Items.AIR),
				new ItemStack(GRAY_SHARK.get()),
				2,
				30,
				0.1f
			));
		}
	}

	public void onInitialize() {
		// The onInitialize method is now empty as the trade system has been updated
	}
}
