import { t } from "elysia";

export const schemas = {
  get: {
    items: {
      query: t.Optional(
        t.Object({
          page: t.Optional(t.Numeric({ minimum: 1, default: 1 })),
          limit: t.Optional(t.Numeric({ minimum: 1, default: 5 })),
          search: t.Optional(t.String()),
        }),
      ),
    },

    item: {
      params: t.Object({
        id: t.Numeric({ minimum: 1 }),
      }),
    },
  },

  post: {
    item: {
      body: t.Object({
        featuresCode: t.String(),
        description: t.String(),
        price: t.Numeric({ minimum: 0 }),
      }),
    },
  },

  delete: {
    item: {
      params: t.Object({
        id: t.Numeric({ minimum: 1 }),
      }),
    },
  },

  put: {
    item: {
      params: t.Object({
        id: t.Numeric({ minimum: 1 }),
      }),
      body: t.Object({
        featuresCode: t.String(),
        description: t.String(),
      }),
    },
  },
};
