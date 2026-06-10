import { t } from "elysia";

export const schemas = {
    get: {
        inventory: {
            query: t.Union([
                t.Object({
                    view: t.Literal("summary"),
                }),
                t.Object({
                    view: t.Literal("boxes"),
                    sequenceNumber: t.Optional(t.Numeric({ minimum: 1, default: undefined })),
                }),
                t.Object({
                    view: t.Literal("items"),
                    search: t.Optional(t.String({ default: undefined }))
                }),
                t.Object({
                    view: t.Literal("item-instances"),
                    itemId: t.Numeric({ minimum: 1 }),
                    sequenceNumber: t.Optional(t.Numeric({ minimum: 1, default: undefined }))
                }),
            ]),
        },
    },
};
