/*
  Warnings:

  - You are about to drop the column `categoryId` on the `item` table. All the data in the column will be lost.
  - You are about to drop the `category` table. If the table is not empty, all the data it contains will be lost.

*/
-- DropForeignKey
ALTER TABLE `item` DROP FOREIGN KEY `Item_categoryId_fkey`;

-- DropIndex
DROP INDEX `Item_categoryId_fkey` ON `item`;

-- AlterTable
ALTER TABLE `item` DROP COLUMN `categoryId`;

-- DropTable
DROP TABLE `category`;
