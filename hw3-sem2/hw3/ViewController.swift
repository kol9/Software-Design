//
//  ViewController.swift
//  hw3
//
//  Created by Nikolay Yarlychenko on 28.03.2022.
//

import Cocoa

fileprivate enum CellIdentifiers {
    static let DateCell = "DateCellID"
    static let EventCell = "EventCellID"
}

struct Item {
    let event: String
    let date: Date
    
    static var example = Self.init(event: "Test event aboba", date: .now)
}

class ViewController: NSViewController, NSTableViewDelegate, NSTableViewDataSource {
    
    @IBOutlet weak var textField: NSTextField!
    @IBOutlet weak var datePicker: NSDatePicker!
    @IBOutlet weak var tableView: NSTableView!
    
    @IBOutlet weak var showHistoryButton: NSButton!
    @IBOutlet weak var prolongeButton: NSButton!
    @IBOutlet weak var inButton: NSButton!
    @IBOutlet weak var exitButton: NSButton!
    
    
    let eventStore = InMemoryEventStore()
    lazy var turnstile = Turnstile(eventStore: eventStore)
    lazy var manager = Manager(eventStore: eventStore)
    
    
    var directoryItems: [Item]? {
        guard let selectedId = Int(textField.stringValue) else {
            return nil
        }
        return eventStore.eventsById(selectedId).compactMap { event in
            Item(event: event.description, date: event.date)
        }
    }
    
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        tableView.delegate = self
        tableView.dataSource = self
        
        showHistoryButton.action = #selector(handleShowHistoryButton)
        prolongeButton.action = #selector(handleProlongeButton)
        inButton.action = #selector(handleInButton)
        exitButton.action = #selector(handleExitButton)
    }
}

// MARK: - Actions

private extension ViewController {
    
    @objc
    func handleInButton() {
        guard let selectedId = Int(textField.stringValue) else {
            return
        }
        turnstile.enter(accountId: selectedId)
        tableView.reloadData()
    }
    
    @objc
    func handleExitButton() {
        guard let selectedId = Int(textField.stringValue) else {
            return
        }
        turnstile.exit(accountId: selectedId)
        tableView.reloadData()
    }
    
    @objc
    func handleProlongeButton() {
        guard let selectedId = Int(textField.stringValue) else {
            return
        }
        
        if eventStore.eventsById(selectedId).isEmpty {
            manager.createAccountWithId(selectedId)
        }
        let date = datePicker.dateValue
        manager.prolongeAccountSubscriptionWithId(selectedId, until: date)
        tableView.reloadData()
    }
    
    @objc
    func handleShowHistoryButton() {
        tableView.reloadData()
    }
}

// MARK: - NSTableViewDataSource

extension ViewController {
    func numberOfRows(in tableView: NSTableView) -> Int {
        return directoryItems?.count ?? 0
    }
    
    func tableView(_ tableView: NSTableView, viewFor tableColumn: NSTableColumn?, row: Int) -> NSView? {
        
        var text: String = ""
        var cellIdentifier: String = ""
        
        let dateFormatter = DateFormatter()
        dateFormatter.dateStyle = .short
        dateFormatter.timeStyle = .short
        
        guard let item = directoryItems?[row] else { return nil }
        
        if tableColumn == tableView.tableColumns[0] {
            text = dateFormatter.string(from: item.date)
            cellIdentifier = CellIdentifiers.DateCell
        } else if tableColumn == tableView.tableColumns[1] {
            cellIdentifier = CellIdentifiers.EventCell
            text = item.event
        }
                
        if let cell = tableView.makeView(withIdentifier: .init(rawValue: cellIdentifier), owner: nil) as? NSTableCellView {
            cell.textField?.stringValue = text
            return cell
        }
        
        return nil
    }
}

