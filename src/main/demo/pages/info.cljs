(ns demo.pages.info
  (:require                                                                                                                                                

    [keechma.next.helix.core :refer [use-sub dispatch]]
    [demo.helix :refer [defnc]]
    [keechma.next.controllers.router :as router]
    [clojure.string]

    [helix.hooks :refer [use-state]]
    [helix.core :as hx :refer [$ <>]]

    ["@mui/material/TextField" :default TextField]

    ["@mui/material/Button" :default Button]
    ["@mui/material/Paper" :default Paper]
    ["@mui/material/Divider" :default Divider]
    ["@mui/material/Typography" :default Typography]
    ["@mui/material/Container" :default Container]

    [taoensso.timbre :as timbre :refer-macros [debug  info  warn  error]]
    [oops.core :refer [ocall oget oset!]]))


(def ctrl :company/compare)

(defnc render
  [props]
  (let [{:keys [paragraphs]} (use-sub props :app)
        {:keys [id]:as router} (use-sub props :router)
        id (js/parseInt id)
        {:keys [title text]} (get paragraphs id)
        [para-title set-para-title] (use-state title)]

    (debug id)
    ($ Container
      {:maxWidth "2xl"}
      ($ TextField
        {:label "Title"
         :onChange #(set-para-title (oget % "target.value"))
         :value para-title})

      ($ Button
        {:onClick #(dispatch props :app :update [id {:title para-title}])}
        "Save")
      ($ Button
        {:onClick #(router/redirect! props :router {:page "home"})}
        "Cancel"))))
