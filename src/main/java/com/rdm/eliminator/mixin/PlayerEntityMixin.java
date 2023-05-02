package com.rdm.eliminator.mixin;


import java.util.UUID;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.mojang.authlib.GameProfile;
import com.rdm.eliminator.manager.EliminatorConfigManager;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.BannedPlayerEntry;
import net.minecraft.server.BannedPlayerList;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.UserCache;
import net.minecraft.world.World;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {

	public PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
		super(entityType, world);
	}

	@SuppressWarnings("deprecation")
	@Inject(method = "onDeath", at = @At("HEAD"), cancellable = true)
	private void eliminator$onDeath(DamageSource causeSource, CallbackInfo info) {
		PlayerEntity player = (PlayerEntity) (Object) this;
		
		if (player != null && player instanceof ServerPlayerEntity) {
			final ServerPlayerEntity serverPlayer = (ServerPlayerEntity) (Object) this;
			final GameProfile serverPlayerProfile = serverPlayer.getGameProfile();
			final UUID serverPlayerUUID = serverPlayer.getUuid();
			MinecraftServer playerServer = serverPlayer.getServer();
			BannedPlayerList curBannedProfiles = serverPlayer.getServer().getPlayerManager().getUserBanList();

			if (causeSource != null) {
				if (causeSource.getAttacker() != null) {
					if (causeSource.getAttacker() instanceof PlayerEntity) {
						if (!curBannedProfiles.contains(serverPlayerProfile)) {
							final BannedPlayerEntry bannedTargetPlayer = new BannedPlayerEntry(serverPlayerProfile);
							
							bannedTargetPlayer.getExpiryDate().setMinutes(EliminatorConfigManager.PVP_DEATH_BAN_INTERVAL);
							curBannedProfiles.add(bannedTargetPlayer);
							serverPlayer.networkHandler.disconnect(Text.of(EliminatorConfigManager.PVP_DEATH_BAN_MESSAGE));
							
							if (EliminatorConfigManager.ENABLE_SERVER_DEATH_BAN_ANNOUNCEMENTS) playerServer.sendMessage(Text.of(EliminatorConfigManager.PVP_DEATH_BAN_MESSAGE_SERVER));
							
							if (playerServer != null) {
								UserCache serverCache = playerServer.getUserCache();
								
								if (serverCache != null) {
									serverCache.getByUuid(serverPlayerUUID).ifPresent((profile) -> {										
										serverCache.byUuid.remove(profile.getId());
										serverCache.byName.remove(profile.getName());
										serverCache.save();
										serverCache.load();
									});
								}
							}
						}
					} else if (causeSource.getAttacker() instanceof LivingEntity && !(causeSource.getAttacker() instanceof PlayerEntity)) {
						if (!curBannedProfiles.contains(serverPlayerProfile)) {
							final BannedPlayerEntry bannedTargetPlayer = new BannedPlayerEntry(serverPlayerProfile);
							
							bannedTargetPlayer.getExpiryDate().setMinutes(EliminatorConfigManager.PVE_DEATH_BAN_INTERVAL);
							curBannedProfiles.add(bannedTargetPlayer);
							serverPlayer.networkHandler.disconnect(Text.of(EliminatorConfigManager.PVE_DEATH_BAN_MESSAGE));
							
							if (EliminatorConfigManager.ENABLE_SERVER_DEATH_BAN_ANNOUNCEMENTS) playerServer.sendMessage(Text.of(EliminatorConfigManager.PVE_DEATH_BAN_MESSAGE_SERVER));
						}
					}
				}
			}
		}
	}

}
