(ns demo.pages.info
  (:require                                                                                                                                                

    [keechma.next.helix.core :refer [use-sub]]
    [demo.helix :refer [defnc]]
    [clojure.string]

    [helix.hooks :refer [use-state]]
    [helix.core :as hx :refer [$ <>]]

    ["@mui/material/TextField" :default TextField]

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
        {:keys [title id text]} (get paragraphs (js/parseInt id))
        [para-title set-para-title] (use-state title)]

    ($ Container
      {:maxWidth "2xl"}
      ($ TextField
        {:onChange #(set-para-title (oget % "target.value"))
         :value para-title}))))

