import { t } from "elysia";

export const schemas = {
  get: {
    itemInstances: {
      query: t.Optional(
        t.Object({
          page: t.Optional(t.Numeric({ minimum: 1, default: 1 })),
          limit: t.Optional(t.Numeric({ minimum: 1, default: 30 })),
          itemId: t.Optional(t.Numeric({ minimum: 1 })),
          boxId: t.Optional(t.Numeric({ minimum: 1 })),
          sequenceNumber: t.Optional(t.Numeric({ minimum: 1 })),
        }),
      ),
    },

    itemInstance: {
      params: t.Object({
        id: t.Numeric({ minimum: 1 }),
      }),
    },
  },

  post: {
    itemInstance: {
      body: t.Object({
        itemId: t.Numeric({ minimum: 1 }),
        boxId: t.Optional(t.Numeric({ minimum: 1 })),
      }),
    },
  },

  delete: {
    itemInstance: {
      params: t.Object({
        id: t.Numeric({ minimum: 1 }),
      }),
    },
  },

  patch: {
    itemInstance: {
      params: t.Object({
        id: t.Numeric({ minimum: 1 }),
      }),
      body: t.Object({
        boxId: t.Numeric({ minimum: 1 }),
      }),
    },
  },
};
