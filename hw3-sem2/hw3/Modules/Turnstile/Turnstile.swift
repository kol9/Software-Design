import Foundation

class Turnstile {
    private let eventStore: EventStore
    
    init(eventStore: EventStore) {
        self.eventStore = eventStore
    }
    
    func enter(accountId: Int) {
        let events = eventStore.eventsById(accountId)
        
        let lastProlongation = events.last { event in
            switch event {
            case .prolonged(_, until: let date):
                return date >= .now
            default:
                return false
            }
        }
        
        guard lastProlongation != nil else {
            eventStore.addEvent(.didFailedEnter(at: .now, reason: .subscriptionExpired), accountId: accountId)
            return
        }
        // has active subscription
        
        let filtered = events.filter { $0.isDidExit || $0.isDidEnter }
        guard
            (filtered.last?.isDidExit ?? false) || filtered.isEmpty
        else {
            eventStore.addEvent(.didFailedEnter(at: .now, reason: .alreadyInside), accountId: accountId)
            return
        }
        
        eventStore.addEvent(.didEnter(at: .now), accountId: accountId)
    }
    
    func exit(accountId: Int) {
        let events = eventStore.eventsById(accountId)
        
        let filtered = events.filter { $0.isDidExit || $0.isDidEnter }
        guard
            filtered.last?.isDidEnter ?? false
        else {
            eventStore.addEvent(.didFailedExit(at: .now, reason: .didNotEnter), accountId: accountId)
            return
        }
        
        eventStore.addEvent(.didExit(at: .now), accountId: accountId)
    }
}
