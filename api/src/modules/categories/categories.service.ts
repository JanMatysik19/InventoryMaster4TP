import {
  deleteOne,
  fetchMany,
  fetchOne,
  insertOne,
} from "./categories.repository";

export const categoriesService = {
  getMany: async (ctx: { page: number; limit: number }) => {
    const result = await fetchMany(ctx);
    return result;
  },

  getOne: async ({ id }: { id: number }) => {
    const result = await fetchOne({ id });
    return result;
  },

  addOne: async (ctx: { code: string }) => {
    const result = await insertOne(ctx);
    return result;
  },

  removeOne: async ({ id }: { id: number }) => {
    const result = await deleteOne({ id });
    return result;
  },
};
