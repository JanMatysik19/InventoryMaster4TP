/*
  Warnings:

  - You are about to drop the column `code` on the `box` table. All the data in the column will be lost.
  - You are about to drop the column `sku` on the `item` table. All the data in the column will be lost.

*/
-- DropIndex
DROP INDEX `Box_code_key` ON `box`;

-- AlterTable
ALTER TABLE `box` DROP COLUMN `code`;

-- AlterTable
ALTER TABLE `item` DROP COLUMN `sku`;
