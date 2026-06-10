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
    sequenceNumber?: number;
  }) => {
    const result = await fetchMany(ctx);
    const tmp = result.data.map(box => ({
      ...box,
      code: `BOX${String(box.id).padStart(3, "0")}`
    }));
    result.data = tmp;
    return result;
  },

  getOne: async ({ id }: { id: number }) => {
    const result = await fetchOne({ id });
    if(!result) return null;

    const tmp = {
      ...result,
      code: `BOX${String(result.id).padStart(3, "0")}`
    }
    return tmp;
  },

  addOne: async () => {
    const result = await insertOne();
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
