import { deleteOne, fetchMany, fetchOne, insertOne, updateOne } from "./items.repository";

export const itemsService = {
  getMany: async (ctx: {
    page: number;
    limit: number;
    search: string;
  }) => {
    const result = await fetchMany(ctx);
    const out = result.data.map(item => ({
      ...item,
      quantity: item._count.itemInstances,
      _count: undefined,
    }));
    
    return {
      data: out,
      total: result.total,
    };
  },

  getOne: async ({ id }: { id: number }) => {
    const result = await fetchOne({ id });
    return {
      ...result,
      quantity: result?._count.itemInstances || 0,
      _count: undefined,
    };
  },

  addOne: async (ctx: {
    featuresCode: string;
    description: string;
    price: number;
  }) => {
    const result = await insertOne(ctx);
    return result;
  },

  removeOne: async ({ id }: { id: number }) => {
    const result = await deleteOne({ id });
    return result;
  },

  updateOne: async (ctx: { id: number; featuresCode: string; description: string; }) => {
    const result = await updateOne(ctx);
    return result;
  }
};
