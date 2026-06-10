import { prisma } from "@/database/prisma.client";

export const fetchMany = async ({
  page,
  limit,
  itemId,
  boxId,
  sequenceNumber,
}: {
  page: number;
  limit: number;
  itemId?: number;
  boxId?: number;
  sequenceNumber?: number;
}) => {
  const skip = (page - 1) * limit;
  const where = {
    itemId,
    boxId,
    sequenceNumber,
  };

  return {
    data: await prisma.itemInstance.findMany({
      skip,
      take: limit,
      where
    }),
    total: Math.ceil((await prisma.itemInstance.count({ where })) / limit),
  };
};

export const fetchOne = async ({ id }: { id: number }) => {
  return await prisma.itemInstance.findFirst({
    where: {
      id: id,
    },
  });
};

export const insertOne = async ({
  itemId,
  boxId,
}: {
  itemId: number;
  boxId?: number;
}) => {
  const lastId = await prisma.itemInstance.aggregate({
    where: {
      itemId,
    },
    _max: {
      sequenceNumber: true,
    },
  });
  const sequenceNumber = (lastId._max.sequenceNumber || 0) + 1;

  return await prisma.itemInstance.create({
    data: {
      itemId,
      boxId,
      sequenceNumber,
    },
    include: {
      box: true,
    },
  });
};

export const deleteOne = async ({ id }: { id: number }) => {
  return await prisma.itemInstance.delete({
    where: {
      id: id,
    },
  });
};

export const updateOneLocation = async ({
  id,
  boxId,
}: {
  id: number;
  boxId: number;
}) => {
  return await prisma.itemInstance.update({
    where: {
      id,
    },
    data: {
      boxId,
    },
  });
};

export const fetchTotalItemInstances = async ({itemId, sequenceNumber}: { itemId?: number; sequenceNumber?: number; }) => {
  return await prisma.itemInstance.count({
    where: {
      itemId,
      sequenceNumber,
    },
  });
};
