import XCTest
@testable import hw8

class EventStatisticTests: XCTestCase {
    
    // MARK: - Constants
    
    private enum Events: String {
        case event1
        case event2
        case event3
    }
    
    private enum Constants {
        static let dec_11_2021: TimeInterval = 1639233977
        
        static let testsAccuracy = 0.0001
        
        static let secondsInHour: TimeInterval = 3600
    }
    
    // MARK: Setup
    
    private let clock = SetableClock(Constants.dec_11_2021)
    private var eventStatistic: EventStatisticProtocol!

    override func setUpWithError() throws {
        clock.now = Constants.dec_11_2021
        eventStatistic = EventStatistic(clock: clock)
    }
    
    
    // MARK: - Helpers
    
    private func produceRandomEvents(withName name: Events) -> Int {
        guard let c: Int = (1...5).randomElement() else { return 0 }
        for _ in 0..<c {
            self.eventStatistic.incEvent(name: name.rawValue)
        }
        return c
    }
    
    // MARK: - Tests
    
    func testOneEvent() {
        /// Given
        /// clock, empty eventStatistic
        
        /// When
        eventStatistic.incEvent(name: Events.event1.rawValue)
        let fiveSecsAfterNow = clock.now + 5
        clock.now = fiveSecsAfterNow
        
        /// Then
        XCTAssertEqual(0.0167, eventStatistic.getEventStatisticBy(name: Events.event1.rawValue), accuracy: Constants.testsAccuracy)
        XCTAssertEqual(0, eventStatistic.getEventStatisticBy(name: Events.event2.rawValue), accuracy: Constants.testsAccuracy)
    }
    
    func testOutOfBoundsEvent() {
        /// Given
        /// clock, empty eventStatistic
        
        /// When
        eventStatistic.incEvent(name: Events.event3.rawValue)
        let oneHourAndAfterNow = clock.now + Constants.secondsInHour
        clock.now = oneHourAndAfterNow
        
        /// Then
        XCTAssertEqual(0, eventStatistic.getEventStatisticBy(name: Events.event3.rawValue), accuracy: Constants.testsAccuracy)
    }
    
    func testCornerCasesEvents() {
        /// Given
        /// clock, empty eventStatistic
        
        /// When
        eventStatistic.incEvent(name: Events.event3.rawValue)
        let fiftyNineMinutesAfterNow = clock.now + Constants.secondsInHour - 1
        clock.now = fiftyNineMinutesAfterNow
        
        /// Then
        XCTAssertEqual(0.0167, eventStatistic.getEventStatisticBy(name: Events.event3.rawValue), accuracy: Constants.testsAccuracy)
    }
    
    func testMultipleEvents() {
        /// Given
        /// clock, empty eventStatistic

        /// When
        var totalEvents1 = 0
        var totalEvents2 = 0
        var totalEvents3 = 0
        for _ in 1..<Int(Constants.secondsInHour) {
            let producedCount1 = self.produceRandomEvents(withName: .event1)
            totalEvents1 += producedCount1
            
            let producedCount2 = self.produceRandomEvents(withName: .event2)
            totalEvents2 += producedCount2

            let producedCount3 = self.produceRandomEvents(withName: .event3)
            totalEvents3 += producedCount3
            
            clock.now = clock.now + 1
        }
        
        /// Then
        let doubleResult1 = Double(totalEvents1) / 60.0
        let doubleResult2 = Double(totalEvents2) / 60.0
        let doubleResult3 = Double(totalEvents3) / 60.0
        
        let totalStats = eventStatistic.getAllEventStatistic()
        
        XCTAssertEqual(doubleResult1, totalStats[Events.event1.rawValue]!, accuracy: Constants.testsAccuracy)
        XCTAssertEqual(doubleResult2, totalStats[Events.event2.rawValue]!, accuracy: Constants.testsAccuracy)
        XCTAssertEqual(doubleResult3, totalStats[Events.event3.rawValue]!, accuracy: Constants.testsAccuracy)
    }
}
