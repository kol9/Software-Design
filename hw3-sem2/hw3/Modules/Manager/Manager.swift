import Foundation

class Manager {
    private let eventStore: EventStore
    
    init(eventStore: EventStore) {
        self.eventStore = eventStore
    }
    
    func createAccountWithId(_ id: Int) {
        eventStore.addEvent(.created(at: .now), accountId: id)
    }
    
    func prolongeAccountSubscriptionWithId(_ id: Int, until: Date) {
        eventStore.addEvent(.prolonged(at: .now, until: until), accountId: id)
    }
}
