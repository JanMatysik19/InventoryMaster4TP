import { t } from "elysia";

export const schemas = {
  get: {
    items: {
      query: t.Optional(
        t.Object({
          page: t.Optional(t.Numeric({ minimum: 1, default: 1 })),
          limit: t.Optional(t.Numeric({ minimum: 1, default: 30 })),
          search: t.Optional(t.String()),
          category: t.Optional(t.String()),
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
        unit: t.Optional(t.String()),
        category: t.String(),
        featuresCode: t.String(),
        description: t.String(),
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
};
