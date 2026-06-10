import { getTotalBoxes } from "../boxes/boxes.repository"
import { fetchTotalItemInstances } from "../itemInstances/itemInstance.repository";
import { fetchPricalbleMany, getTotalItems } from "../items/items.repository"


export type TotalBoxes = {
    sequenceNumber?: number;
}
export type TotalItems = {
    search?: string;
}
export type TotalItemInstances = {
    itemId?: number;
    sequenceNumber?: number;
}

export const inventoryService = {
    async getTotalBoxes(ctx: TotalBoxes) {
        return {
            totalBoxes: await getTotalBoxes({ id: ctx.sequenceNumber })
        };
    },

    async getTotalItems(ctx: TotalItems) {
        return {
            totalItems: await getTotalItems({ search: ctx.search })
        }
    },

    async getTotalItemInstances(ctx: TotalItemInstances) {
        return {
            totalItemInstances: await fetchTotalItemInstances({ itemId: ctx.itemId, sequenceNumber: ctx.sequenceNumber })
        }
    },

    async getInventoryTotalValue() {
        const result = await fetchPricalbleMany({});
        const totalValue = result.reduce((sum, item) => sum + Number(item.price) * item._count.itemInstances, 0);
        return {
            totalValue
        }
    }
}