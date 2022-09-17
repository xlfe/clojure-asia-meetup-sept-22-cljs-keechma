(ns demo.pages
  (:require                                                                                                                                                
    [demo.appbar :as appbar]
    [demo.helix :refer [defnc]]

    [keechma.next.helix.core :refer [with-keechma use-sub]]
    [helix.core :as hx :refer [$ <>]]

    ["@mui/material/styles" :as styles :refer [ThemeProvider createTheme]]                                                                         
    ["@mui/material/CssBaseline" :default mui-CssBaseline]                                                                                         
    ["@mui/material/Container" :default Container]
    [taoensso.timbre :as timbre :refer-macros [debug info warn error]]))

(def theme-
  {:theme 
   (createTheme 
     (clj->js
       {
        :palette {:mode "light"
                  :primary {:main "#5881D8"}
                  :secondary {:main "#62B132"}}
        ; This must match the font family available from index.html
        :typography {:fontFamily "Roboto"}}))})


(defnc RenderPage
  [props]
  (let [
        {:keys [page] :as router} (use-sub props :router)
        show-page  (or page ($ "div"))]
    ($ show-page {& props})))

(defnc MainRenderer
  [props]
  ($ ThemeProvider
    {& theme-}
    ($ mui-CssBaseline
      (<>
        ($ appbar/render {& props})
        ($ Container
          {:maxWidth "md" :sx #js{:pt 4}}
          ($ RenderPage {& props}))))))     

(def main- (with-keechma MainRenderer))
                    
