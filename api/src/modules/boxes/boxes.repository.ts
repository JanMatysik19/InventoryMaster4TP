import { prisma } from "@/database/prisma.client";

export const fetchMany = async ({
  page,
  limit,
}: {
  page: number;
  limit: number;
}) => {
  const skip = (page - 1) * limit;
  return {
    data: await prisma.box.findMany({
      skip,
      take: limit,
    }),
    total: Math.ceil((await prisma.box.count()) / limit),
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
  const lastId = await prisma.box.aggregate({
    _max: {
      id: true,
    },
  });
  const code = `BOX${String((lastId._max.id || 0) + 1).padStart(3, "0")}`;

  return await prisma.box.create({
    data: {
      code,
    },
  });
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
