import {
  deleteOne,
  fetchMany,
  fetchOne,
  insertOne,
  updateOneLocation,
} from "./itemInstance.repository";

export const itemInstancesService = {
  getMany: async (ctx: {
    page: number;
    limit: number;
    itemId?: number;
    boxId?: number;
  }) => {
    const result = await fetchMany(ctx);
    return result;
  },

  getOne: async ({ id }: { id: number }) => {
    const result = await fetchOne({ id });
    return result;
  },

  addOne: async (ctx: { boxId?: number; itemId: number }) => {
    const result = await insertOne(ctx);
    return result;
  },

  removeOne: async ({ id }: { id: number }) => {
    const result = await deleteOne({ id });
    return result;
  },

  moveOne: async (ctx: { id: number; boxId: number }) => {
    const result = await updateOneLocation(ctx);
    return result;
  },
};
