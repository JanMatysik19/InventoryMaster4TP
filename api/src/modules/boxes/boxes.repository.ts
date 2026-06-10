import { prisma } from "@/database/prisma.client";

export const fetchMany = async ({
  page,
  limit,
  sequenceNumber,
}: {
  page: number;
  limit: number;
  sequenceNumber?: number;
}) => {
  const skip = (page - 1) * limit;
  const where = sequenceNumber ? {
    id: sequenceNumber
  } : undefined

  return {
    data: await prisma.box.findMany({
      skip,
      take: limit,
      where,
    }),
    total: Math.ceil((await prisma.box.count({ where })) / limit),
  };
};

export const fetchOne = async ({ id }: { id: number }) => {
  return await prisma.box.findFirst({
    where: {
      id: id,
    },
  });
};

export const insertOne = async () => {
  return await prisma.box.create({});
};

export const deleteOne = async ({ id }: { id: number }) => {
  return await prisma.box.delete({
    where: {
      id: id,
    },
  });
};

export const updateOneLocation = async ({
  id,
  shelfId,
}: {
  id: number;
  shelfId: number;
}) => {
  return await prisma.box.update({
    where: {
      id,
    },
    data: {
      shelfId,
    },
  });
};

export const getTotalBoxes = async ({ id }: { id?: number}) => {
  return await prisma.box.count({
    where: {
      id
    }
  });
}
