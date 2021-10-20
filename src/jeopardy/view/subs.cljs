(ns jeopardy.view.subs
  (:require [re-frame.core :as re-frame]))

(re-frame/reg-sub
  ::clicks
  (fn [db] (:clicks db)))

(re-frame/reg-sub
  ::username
  (fn [db] (:username db)))


(re-frame/reg-sub
  ::service-response
  (fn [db] (:service-response db)))
