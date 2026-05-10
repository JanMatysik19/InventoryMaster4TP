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
    data: await prisma.category.findMany({
      skip,
      take: limit,
    }),
    total: Math.ceil((await prisma.category.count()) / limit),
  };
};

export const fetchOne = async ({ id }: { id: number }) => {
  return await prisma.category.findFirst({
    where: {
      id: id,
    },
  });
};

export const insertOne = async ({ code }: { code: string }) => {
  return await prisma.category.create({
    data: {
      code,
    },
  });
};

export const deleteOne = async ({ id }: { id: number }) => {
  return await prisma.item.delete({
    where: {
      id: id,
    },
  });
};
