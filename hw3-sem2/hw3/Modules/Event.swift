import Foundation

public enum Event: CustomStringConvertible {
    public var description: String {
        switch self {
        case .created(_):
            return "created"
        case .prolonged(_, let until):
            return "prolonged until \(until)"
        case .didEnter:
            return "didEnter"
        case .didExit:
            return "didExit"
        case .didFailedEnter:
            return "didFailedEnter"
        case .didFailedExit:
            return "didFailedExit"
        }
    }
    
        
    // MARK: - AccountSubscriptionEvent
    
    case created(at: Date)
    case prolonged(at: Date, until: Date)

    // MARK: - AccountUsageEvent
    
    case didEnter(at: Date)
    case didExit(at: Date)
    
    case didFailedEnter(at: Date, reason: EnterFailureReason)
    case didFailedExit(at: Date, reason: ExitFailureReason)
    
    public enum EnterFailureReason {
        case alreadyInside
        case subscriptionExpired
        case other(String?)
    }
    
    public enum ExitFailureReason {
        case didNotEnter
        case other(String?)
    }
    
    // MARK: - Helpers
    
    var date: Date {
        switch self {
        case .created(let at):
            return at
        case .prolonged(let at, _):
            return at
        case .didEnter(let at):
            return at
        case .didExit(let at):
            return at
        case .didFailedEnter(let at, _):
            return at
        case .didFailedExit(let at, _):
            return at
        }
    }
    
    var isProlonged: Bool {
        switch self {
        case .created:
            return true
        default:
            return false
        }
    }
    
    var isDidEnter: Bool {
        switch self {
        case .didEnter:
            return true
        default:
            return false
        }
    }
    
    var isDidExit: Bool {
        switch self {
        case .didExit:
            return true
        default:
            return false
        }
    }
}
