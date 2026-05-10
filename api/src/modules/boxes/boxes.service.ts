import {
  deleteOne,
  fetchMany,
  fetchOne,
  insertOne,
  updateOneLocation,
} from "./boxes.repository";

export const boxesService = {
  getMany: async (ctx: {
    page: number;
    limit: number;
    itemId?: number;
    shelfId?: number;
  }) => {
    const result = await fetchMany(ctx);
    return result;
  },

  getOne: async ({ id }: { id: number }) => {
    const result = await fetchOne({ id });
    return result;
  },

  addOne: async (ctx: { shelfId?: number }) => {
    const result = await insertOne(ctx);
    return result;
  },

  removeOne: async ({ id }: { id: number }) => {
    const result = await deleteOne({ id });
    return result;
  },

  moveOne: async (ctx: { id: number; shelfId: number }) => {
    const result = await updateOneLocation(ctx);
    return result;
  },
};
