/*
  Warnings:

  - You are about to drop the column `updatedAt` on the `box` table. All the data in the column will be lost.
  - You are about to drop the column `updatedAt` on the `category` table. All the data in the column will be lost.
  - You are about to drop the column `updatedAt` on the `item` table. All the data in the column will be lost.
  - You are about to drop the column `updatedAt` on the `iteminstance` table. All the data in the column will be lost.

*/
-- AlterTable
ALTER TABLE `box` DROP COLUMN `updatedAt`;

-- AlterTable
ALTER TABLE `category` DROP COLUMN `updatedAt`;

-- AlterTable
ALTER TABLE `item` DROP COLUMN `updatedAt`;

-- AlterTable
ALTER TABLE `iteminstance` DROP COLUMN `updatedAt`;
