import { t } from "elysia";

export const schemas = {
  get: {
    boxes: {
      query: t.Optional(
        t.Object({
          page: t.Optional(t.Numeric({ minimum: 1, default: 1 })),
          limit: t.Optional(t.Numeric({ minimum: 1, default: 30 })),
          shelfId: t.Optional(t.Numeric({ minimum: 1 })),
        }),
      ),
    },

    box: {
      params: t.Object({
        id: t.Numeric({ minimum: 1 }),
      }),
    },
  },

  post: {
    box: {
      body: t.Object({
        shelfId: t.Optional(t.Numeric({ minimum: 1 })),
      }),
    },
  },

  delete: {
    box: {
      params: t.Object({
        id: t.Numeric({ minimum: 1 }),
      }),
    },
  },

  patch: {
    box: {
      params: t.Object({
        id: t.Numeric({ minimum: 1 }),
      }),
      body: t.Object({
        shelfId: t.Numeric({ minimum: 1 }),
      }),
    },
  },
};
