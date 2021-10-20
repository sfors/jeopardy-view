(ns ^:figwheel-hooks jeopardy.view.main
  (:require
    [goog.dom :as gdom]
    [reagent.dom :as rdom]
    [re-frame.core :as re-frame]
    [jeopardy.view.events :as events]
    [jeopardy.view.subs :as subs]))

;; define your app data so that it doesn't get over-written on reload
(defn get-app-element []
  (gdom/getElement "app"))

(defn app-component
  []
  (let [clicks (deref (re-frame/subscribe [::subs/clicks]))
        tdStyle {:padding "1rem" :color "#DDD" :background-color "blue" :background "linear-gradient(135deg, rgba(120,120,255,1) 0%, rgba(50,50,255,1) 100%)"}]
    [:div
     [:h1 "Jeopardy"]
     [:table {:style {:background-color "black" :text-align "center"}}
      [:tr [:th {:style tdStyle} "category1"] [:th {:style tdStyle} "category2"] [:th {:style tdStyle} "category3"] [:th {:style tdStyle} "category4"] [:th {:style tdStyle} "category5"] [:th {:style tdStyle} "category6"]]
      [:tr [:td {:style tdStyle} "200"] [:td {:style tdStyle} "200"] [:td {:style tdStyle} "200"] [:td {:style tdStyle} "200"] [:td {:style tdStyle} "200"] [:td {:style tdStyle} "200"]]
      [:tr [:td {:style tdStyle} "400"] [:td {:style tdStyle} "400"] [:td {:style tdStyle} "400"] [:td {:style tdStyle} "400"] [:td {:style tdStyle} "400"] [:td {:style tdStyle} "400"]]
      [:tr [:td {:style tdStyle} "600"] [:td {:style tdStyle} "600"] [:td {:style tdStyle} "600"] [:td {:style tdStyle} "600"] [:td {:style tdStyle} "600"] [:td {:style tdStyle} "600"]]
      [:tr [:td {:style tdStyle} "800"] [:td {:style tdStyle} "800"] [:td {:style tdStyle} "800"] [:td {:style tdStyle} "800"] [:td {:style tdStyle} "800"] [:td {:style tdStyle} "800"]]
      [:tr [:td {:style tdStyle} "1000"] [:td {:style tdStyle} "1000"] [:td {:style tdStyle} "1000"] [:td {:style tdStyle} "1000"] [:td {:style tdStyle} "1000"] [:td {:style tdStyle} "1000"]]
     ]
    [:p {:style {:color "red"}} (str "Clicks: " clicks)]
    [:button
     {:on-click (fn []
                  (re-frame/dispatch [::events/button-clicked]))}
     "Click me!"] ] ) )


(defn mount [el]
  (rdom/render [app-component] el))

(defn mount-app-element []
  (when-let [el (get-app-element)]
    (mount el)))

;; conditionally start your application based on the presence of an "app" element
;; this is particularly helpful for testing this ns without launching the app
(mount-app-element)

;; specify reload hook with ^;after-load metadata
(defn on-reload
  {:after-load true}
  []
  (mount-app-element)
  ;; optionally touch your app-state to force rerendering depending on
  ;; your application
  ;; (swap! app-state update-in [:__figwheel_counter] inc)
  )


(defn init []
  (re-frame/dispatch [::events/initialize-db]))

(when (= (deref re-frame.db/app-db) {})
  (init))
