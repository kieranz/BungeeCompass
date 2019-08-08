public class ScrollerInventory {

    public ArrayList<Inventory> pages = new ArrayList<Inventory>();
    public UUID id;
    public int currpage = 0;
    public static HashMap<UUID, ScrollerInventory> users = new HashMap<UUID, ScrollerInventory>();
    public ScrollerInventory(ArrayList<ItemStack> items, String name, Player p) {
        this.id = UUID.randomUUID();
        Inventory page = getBlankPage(name);
        for (int i = 0;i < items.size(); i++){
            if (page.firstEmpty() == 46) {
                pages.add(page);
                page = getBlankPage(name);
                page.addItem(items.get(i));
            } else {
                page.addItem(items.get(i));
            }
        }
        pages.add(page);
        //open page 0 for the specified player
        p.openInventory(pages.get(currpage));
        users.put(p.getUniqueId(), this);
    }



   public static final String nextPageName = ChatColor.AQUA + "Next Page";
   public static final String previousPageName = ChatColor.AQUA + "Previous Page";
   public Inventory getBlankPage(String name) {
       Inventory page = Bukkit.createInventory(null, 54, name);

       ItemStack nextpage = XMaterial.valueOf(LIME_STAINED_GLASS_PANE).parseItem();
       nextpage.setAmount(1);

       ItemMeta meta = nextpage.getItemMeta();
       meta.setDisplayName(nextPageName);

       nextpage.setItemMeta(meta);

       ItemStack prevpage = XMaterial.fromString(RED_STAINED_GLASS_PANE).parseItem();
       prevpage.setAmount(1);

       meta = prevpage.getItemMeta();
       meta.setDisplayName(previousPageName);

       prevpage.setItemMeta(meta);


       page.setItem(53, nextpage);
       page.setItem(45, prevpage);
       return page;
    }

    @EventHandler(ignoreCancelled = true)
    public void onClick(InventoryClickEvent event){
       if (!(event.getWhoClicked() instanceof Player)) return;
       Player p = (Player) event.getWhoClicked();
       //Get the current scroller inventory the player is looking at, if the player is looking at one.
       if (!ScrollerInventory.users.containsKey(p.getUniqueId())) return;
       ScrollerInventory inv = ScrollerInventory.users.get(p.getUniqueId());
       if (event.getCurrentItem() == null) return;
       if (event.getCurrentItem().getItemMeta() == null) return;
       if (event.getCurrentItem().getItemMeta().getDisplayName() == null) return;
       //If the pressed item was a nextpage button
       if (event.getCurrentItem().getItemMeta().getDisplayName().equals(ScrollerInventory.nextPageName)){
            event.setCancelled(true);
            //If there is no next page, don't do anything
            if (inv.currpage >= inv.pages.size()-1){
                return;
            } else {
                //Next page exists, flip the page
                inv.currpage += 1;
                p.openInventory(inv.pages.get(inv.currpage));
            }
                        //if the pressed item was a previous page button
        } else if (ScrollerInventory.previousPageName)) {
             event.setCancelled(true);
             //If the page number is more than 0 (So a previous page exists)
             if (inv.currpage > 0){
             //Flip to previous page
                 inv.currpage -= 1;
                 p.openInventory(inv.pages.get(inv.currpage));
             }
        }
    }
}
