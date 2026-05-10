/*
  Warnings:

  - Added the required column `squenceNumber` to the `ItemInstance` table without a default value. This is not possible if the table is not empty.

*/
-- AlterTable
ALTER TABLE `iteminstance` ADD COLUMN `squenceNumber` INTEGER NOT NULL;
