/*
  Warnings:

  - You are about to drop the column `squenceNumber` on the `iteminstance` table. All the data in the column will be lost.
  - Added the required column `sequenceNumber` to the `ItemInstance` table without a default value. This is not possible if the table is not empty.

*/
-- AlterTable
ALTER TABLE `iteminstance` DROP COLUMN `squenceNumber`,
    ADD COLUMN `sequenceNumber` INTEGER NOT NULL;
