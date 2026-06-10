-- DropForeignKey
ALTER TABLE `iteminstance` DROP FOREIGN KEY `ItemInstance_itemId_fkey`;

-- AddForeignKey
ALTER TABLE `ItemInstance` ADD CONSTRAINT `ItemInstance_itemId_fkey` FOREIGN KEY (`itemId`) REFERENCES `Item`(`id`) ON DELETE CASCADE ON UPDATE CASCADE;
