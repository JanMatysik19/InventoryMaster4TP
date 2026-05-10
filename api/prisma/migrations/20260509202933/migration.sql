/*
  Warnings:

  - You are about to drop the column `sequenceNumber` on the `iteminstance` table. All the data in the column will be lost.

*/
-- DropIndex
DROP INDEX `ItemInstance_sequenceNumber_key` ON `iteminstance`;

-- AlterTable
ALTER TABLE `iteminstance` DROP COLUMN `sequenceNumber`;
