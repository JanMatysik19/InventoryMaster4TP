import { Elysia } from "elysia";
import { schemas } from "./inventory.schema";
import { inventoryService } from "./inventory.service";

export const inventoryRoutes = new Elysia({ prefix: "/inventory" })
  .get(
    "/",
    async ({ query, status }) => {
        const view = query.view;
        
        switch(view) {
            case "summary": {
                return {
                    ...(await inventoryService.getTotalItems({})),
                    ...(await inventoryService.getTotalBoxes({})),
                    ...(await inventoryService.getTotalItemInstances({})),
                    ...(await inventoryService.getInventoryTotalValue())
                };
            }
            case "boxes": {
                const sequenceNumber = query.sequenceNumber;
                return {
                    ...(await inventoryService.getTotalBoxes({ sequenceNumber }))
                };
            }
            case "item-instances": {
                const itemId = query.itemId;
                const sequenceNumber = query.sequenceNumber;
                return {
                    ...(await inventoryService.getTotalItemInstances({ itemId, sequenceNumber }))
                };
            }
            case "items": {
                const search = query.search;
                return {
                    ...(await inventoryService.getTotalItems({ search }))
                };
            }
            default: {
                return status("Bad Request");
            }
        }
    },
    {
      query: schemas.get.inventory.query,
    },
  );
