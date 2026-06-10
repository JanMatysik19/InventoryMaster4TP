import { prisma } from "@/database/prisma.client";
import { Prisma } from "@/generated/prisma";

export const fetchMany = async ({
  page,
  limit,
  search,
}: {
  page: number;
  limit: number;
  search: string;
}) => {
  const skip = (page - 1) * limit;
  const where = {
    OR: [
      { featuresCode: { contains: search } },
      { description: { contains: search } },
    ],
  };

  return {
    data: await prisma.item.findMany({
      skip,
      take: limit,
      where,
      include: {
        _count: {
          select: {
            itemInstances: true
          }
        }
      }
    }),
    total: Math.ceil((await prisma.item.count({ where })) / limit),
  };
};

export const fetchPricalbleMany = async ({}) => {
  return await prisma.item.findMany({
      include: {
        _count: {
          select: {
            itemInstances: true
          }
        }
      }
    });
}

export const fetchOne = async ({ id }: { id: number }) => {
  return await prisma.item.findFirst({
    where: {
      id: id,
    },
    include: {
      _count: {
        select: {
          itemInstances: true
        }
      }
    }
  });
};

export const insertOne = async ({
  featuresCode,
  description,
  price,
}: {
  featuresCode: string;
  description: string;
  price: number;
}) => {
  return await prisma.item.create({
    data: {
      description,
      featuresCode,
      price: new Prisma.Decimal(price)
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

export const updateOne = async ({ id, featuresCode, description }: { id: number; featuresCode: string; description: string; }) => {
  return await prisma.item.update({
    where: {
      id: id,
    },
    data: {
      featuresCode,
      description,
    }
  });
};

export const getTotalItems = async ({ search }: { search?: string }) => {
  return await prisma.item.count({
    where: search ? {
      OR: [
        { featuresCode: { contains: search } },
        { description: { contains: search } },
      ],
    } : undefined,
  })
}