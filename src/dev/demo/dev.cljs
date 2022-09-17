(ns demo.dev
  "From https://github.com/lilactown/helix/blob/master/docs/experiments.md#fast-refresh"
  (:require
   [helix.experimental.refresh :as r]))

;; inject-hook! needs to run on application start.
;; For ease, we run it at the top level.
;; This function adds the react-refresh runtime to the page
(r/inject-hook!)

;; shadow-cljs allows us to annotate a function name with `:dev/after-load`
;; to signal that it should be run after any code reload. We call the `refresh!`
;; function, which will tell react to refresh any components which have a
;; signature created by turning on the `:fast-refresh` feature flag.
(defn ^:dev/after-load refresh []
  (r/refresh!))
