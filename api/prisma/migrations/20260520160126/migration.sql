/*
  Warnings:

  - You are about to drop the column `shelfId` on the `box` table. All the data in the column will be lost.
  - You are about to drop the `rack` table. If the table is not empty, all the data it contains will be lost.
  - You are about to drop the `shelf` table. If the table is not empty, all the data it contains will be lost.

*/
-- DropForeignKey
ALTER TABLE `box` DROP FOREIGN KEY `Box_shelfId_fkey`;

-- DropForeignKey
ALTER TABLE `shelf` DROP FOREIGN KEY `Shelf_rackId_fkey`;

-- DropIndex
DROP INDEX `Box_shelfId_fkey` ON `box`;

-- AlterTable
ALTER TABLE `box` DROP COLUMN `shelfId`;

-- DropTable
DROP TABLE `rack`;

-- DropTable
DROP TABLE `shelf`;
