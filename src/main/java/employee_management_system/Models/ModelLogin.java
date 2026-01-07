/**
 * @auhtor Ramadan ismaeL
 */

package employee_management_system.Models;

import employee_management_system.Views.ViewsFactory;

public class ModelLogin {
    private static ModelLogin modelLogin;
    private final ViewsFactory viewsFactory;

    private ModelLogin() {
        this.viewsFactory = new ViewsFactory();
    }

    public static synchronized ModelLogin getInstance() {
        if(modelLogin == null) {
            modelLogin = new ModelLogin();
        }

        return modelLogin;
    }

    public ViewsFactory getViewsFactory() {
        return this.viewsFactory;
    }
}
