import { prisma } from "@/database/prisma.client";

export const fetchMany = async ({
  page,
  limit,
  search,
  category,
}: {
  page: number;
  limit: number;
  search: string;
  category: string;
}) => {
  const skip = (page - 1) * limit;
  return {
    data: await prisma.item.findMany({
      skip,
      take: limit,
      where: {
        AND: [
          {
            OR: [
              { sku: { contains: search } },
              { description: { contains: search } },
            ],
          },
          category ? { category: { code: { equals: category || "" } } } : {},
        ],
      },
    }),
    total: Math.ceil((await prisma.item.count()) / limit),
  };
};

export const fetchOne = async ({ id }: { id: number }) => {
  return await prisma.item.findFirst({
    where: {
      id: id,
    },
  });
};

export const insertOne = async ({
  unit,
  category,
  featuresCode,
  description,
}: {
  unit?: string;
  category: string;
  featuresCode: string;
  description: string;
}) => {
  return await prisma.item.create({
    data: {
      unit,
      description,
      featuresCode,
      category: { connect: { code: category } },
      sku: `${category}-${featuresCode}`,
    },
    include: {
      category: true,
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
