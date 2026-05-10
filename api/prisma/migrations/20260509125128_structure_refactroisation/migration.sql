/*
  Warnings:

  - You are about to drop the column `categoryCode` on the `item` table. All the data in the column will be lost.
  - You are about to drop the column `name` on the `item` table. All the data in the column will be lost.
  - You are about to drop the column `sequenceNumber` on the `item` table. All the data in the column will be lost.
  - You are about to drop the `stock` table. If the table is not empty, all the data it contains will be lost.
  - Added the required column `updatedAt` to the `Box` table without a default value. This is not possible if the table is not empty.
  - Added the required column `categoryId` to the `Item` table without a default value. This is not possible if the table is not empty.
  - Added the required column `description` to the `Item` table without a default value. This is not possible if the table is not empty.
  - Added the required column `updatedAt` to the `Item` table without a default value. This is not possible if the table is not empty.

*/
-- DropForeignKey
ALTER TABLE `stock` DROP FOREIGN KEY `Stock_boxId_fkey`;

-- DropForeignKey
ALTER TABLE `stock` DROP FOREIGN KEY `Stock_itemId_fkey`;

-- DropForeignKey
ALTER TABLE `stock` DROP FOREIGN KEY `Stock_shelfId_fkey`;

-- DropIndex
DROP INDEX `Item_sku_key` ON `item`;

-- AlterTable
ALTER TABLE `box` ADD COLUMN `createdAt` DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
    ADD COLUMN `updatedAt` DATETIME(3) NOT NULL;

-- AlterTable
ALTER TABLE `item` DROP COLUMN `categoryCode`,
    DROP COLUMN `name`,
    DROP COLUMN `sequenceNumber`,
    ADD COLUMN `categoryId` INTEGER NOT NULL,
    ADD COLUMN `createdAt` DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
    ADD COLUMN `description` VARCHAR(191) NOT NULL,
    ADD COLUMN `updatedAt` DATETIME(3) NOT NULL;

-- DropTable
DROP TABLE `stock`;

-- CreateTable
CREATE TABLE `Category` (
    `id` INTEGER NOT NULL AUTO_INCREMENT,
    `code` VARCHAR(191) NOT NULL,
    `createdAt` DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
    `updatedAt` DATETIME(3) NOT NULL,

    UNIQUE INDEX `Category_code_key`(`code`),
    PRIMARY KEY (`id`)
) DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- CreateTable
CREATE TABLE `ItemInstance` (
    `id` INTEGER NOT NULL AUTO_INCREMENT,
    `sequenceNumber` INTEGER NOT NULL,
    `itemId` INTEGER NOT NULL,
    `boxId` INTEGER NULL,
    `createdAt` DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
    `updatedAt` DATETIME(3) NOT NULL,

    UNIQUE INDEX `ItemInstance_sequenceNumber_key`(`sequenceNumber`),
    INDEX `ItemInstance_itemId_idx`(`itemId`),
    INDEX `ItemInstance_boxId_idx`(`boxId`),
    PRIMARY KEY (`id`)
) DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- AddForeignKey
ALTER TABLE `Item` ADD CONSTRAINT `Item_categoryId_fkey` FOREIGN KEY (`categoryId`) REFERENCES `Category`(`id`) ON DELETE RESTRICT ON UPDATE CASCADE;

-- AddForeignKey
ALTER TABLE `ItemInstance` ADD CONSTRAINT `ItemInstance_itemId_fkey` FOREIGN KEY (`itemId`) REFERENCES `Item`(`id`) ON DELETE RESTRICT ON UPDATE CASCADE;

-- AddForeignKey
ALTER TABLE `ItemInstance` ADD CONSTRAINT `ItemInstance_boxId_fkey` FOREIGN KEY (`boxId`) REFERENCES `Box`(`id`) ON DELETE SET NULL ON UPDATE CASCADE;
