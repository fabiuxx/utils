/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.jsf.models.selection;

import fa.gs.utils.collections.Lists;
import fa.gs.utils.misc.errors.Errors;
import java.util.Collection;
import java.util.Objects;
import javax.faces.component.UIComponent;
import javax.faces.component.UISelectItems;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Fabio A. González Sosa
 */
@FacesConverter("fags.SelectionModelItemConverter")
public class SelectionModelItemConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String itemKey) {
        Collection<SelectionModelItem> items = collectSelectionModelItems(component);
        for (SelectionModelItem item : items) {
            if (Objects.equals(itemKey, item.getItemKey())) {
                //return item.getValue();
                return item;
            }
        }

        return null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value == null) {
            return "";
        }

        if (value instanceof SelectionModelItem) {
            SelectionModelItem item = (SelectionModelItem) value;
            return item.getItemKey();
        }

        throw Errors.illegalArgument("Se esperaba un elemento de tipo '%s' en vez de '%s'", SelectionModelItem.class.getCanonicalName(), value.getClass().getCanonicalName());
    }

    public static Collection<SelectionModelItem> collectSelectionModelItems(UIComponent parent) {
        Collection<SelectionModelItem> items = Lists.empty();

        // Procesar componentes UISelectItems, no se da soporte a UISelectItem.
        for (UIComponent child : parent.getChildren()) {
            if (child instanceof UISelectItems) {
                UISelectItems uiSelectItems = (UISelectItems) child;
                collectSelectionModelItems(items, uiSelectItems);
            }
        }

        return items;
    }

    private static void collectSelectionModelItems(Collection<SelectionModelItem> items, UISelectItems uiSelectItems) {
        Object uiSelectItemsValue = uiSelectItems.getValue();
        if (uiSelectItemsValue instanceof Collection) {
            Collection selectionModelItems = (Collection) uiSelectItemsValue;
            for (Object selectionModelItem : selectionModelItems) {
                if (selectionModelItem instanceof SelectionModelItem) {
                    SelectionModelItem item = (SelectionModelItem) selectionModelItem;
                    items.add(item);
                } else {
                    throw Errors.illegalArgument("Se esperaba un item de tipo '%s' en vez de '%s'.", SelectionModelItem.class.getCanonicalName(), selectionModelItem.getClass().getCanonicalName());
                }
            }
        }
    }

}
