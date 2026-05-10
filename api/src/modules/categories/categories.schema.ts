import { t } from "elysia";

export const schemas = {
  get: {
    categories: {
      query: t.Optional(
        t.Object({
          page: t.Optional(t.Numeric({ minimum: 1, default: 1 })),
          limit: t.Optional(t.Numeric({ minimum: 1, default: 30 })),
        }),
      ),
    },

    category: {
      params: t.Object({
        id: t.Numeric({ minimum: 1 }),
      }),
    },
  },

  post: {
    category: {
      body: t.Object({
        code: t.String(),
      }),
    },
  },

  delete: {
    category: {
      params: t.Object({
        id: t.Numeric({ minimum: 1 }),
      }),
    },
  },
};
