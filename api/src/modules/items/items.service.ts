import { deleteOne, fetchMany, fetchOne, insertOne } from "./items.repository";

export const itemsService = {
  getMany: async (ctx: {
    page: number;
    limit: number;
    search: string;
    category: string;
  }) => {
    const result = await fetchMany(ctx);
    return result;
  },

  getOne: async ({ id }: { id: number }) => {
    const result = await fetchOne({ id });
    return result;
  },

  addOne: async (ctx: {
    unit?: string;
    category: string;
    featuresCode: string;
    description: string;
  }) => {
    const result = await insertOne(ctx);
    return result;
  },

  removeOne: async ({ id }: { id: number }) => {
    const result = await deleteOne({ id });
    return result;
  },
};
